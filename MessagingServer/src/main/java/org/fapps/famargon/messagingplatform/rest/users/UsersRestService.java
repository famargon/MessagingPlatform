package org.fapps.famargon.messagingplatform.rest.users;

import org.fapps.famargon.messagingplatform.businesslogic.users.MessagingUsersService;
import org.fapps.famargon.messagingplatform.datamodel.MessagingUser;
import org.fapps.famargon.messagingplatform.datamodel.SystemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/user")
public class UsersRestService {

	@Autowired
	private MessagingUsersService communicationsUsersService;
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	SystemResponse saveUser(@RequestBody RequestObjectUser user){
		return communicationsUsersService.saveUser(user);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	@ResponseBody
	MessagingUser getUser(@PathVariable String id){
		return communicationsUsersService.getUser(id);
	}
	
	//TODO delete solo con permisos de admin
	
}
