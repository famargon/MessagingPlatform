package org.fapps.famargon.communications.rest.messages;

import java.util.List;

import org.fapps.famargon.communications.businesslogic.communications.CommunicationsService;
import org.fapps.famargon.communications.datamodel.SystemMessage;
import org.fapps.famargon.communications.datamodel.SystemResponse;
import org.fapps.famargon.communications.security.CommunicationsSystemRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/messages")
public class MessagesRestService {

	@Autowired
	private CommunicationsService communicationsService;

	
	@RequestMapping(value="/directSending", method=RequestMethod.POST)
	@ResponseBody
	public SystemResponse directSending(@RequestBody SystemMessage message){
		return communicationsService.directSending(message);
	}
	
	@RequestMapping(value="/send", method=RequestMethod.POST)
	public SystemResponse send(@RequestBody SystemMessage message){
		return communicationsService.systemSend(message);
	}
	
	@RequestMapping(value="/receive", method=RequestMethod.GET)
	public List<SystemMessage> receive(){
		String user = SecurityContextHolder.getContext().getAuthentication().getName();
		return communicationsService.receiveByUser(user);
	}
	
	@RequestMapping(value="/receive/{connectionId}", method=RequestMethod.GET)
	public List<SystemMessage> receiveMessage(@PathVariable String connectionId){
		return communicationsService.receiveByConnectionId(connectionId);
	}
	
}
