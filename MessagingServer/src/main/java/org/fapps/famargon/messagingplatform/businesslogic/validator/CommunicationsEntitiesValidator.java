package org.fapps.famargon.messagingplatform.businesslogic.validator;

import java.util.Arrays;

import org.apache.commons.lang3.Validate;
import org.fapps.famargon.messagingplatform.datamodel.SystemMessage;
import org.fapps.famargon.messagingplatform.datamodel.SystemResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CommunicationsEntitiesValidator {

	public SystemResponse validateSystemEntry(SystemMessage message){
		try{
			Validate.notNull(message, "Message is null");
			Validate.notNull(message.getSource(), "Source is required");
			Validate.notNull(message.getDestination(), "Destination is required");
			Validate.notNull(message.getContent().length>0, "Content is empty");
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Validate.isTrue(username.equals(message.getSource()), "Forbidden");
			message.setReceivingInstant(null);
			message.setSendingInstant(null);
			message.setReceivingInstant(null);
		}catch(RuntimeException npe){
			SystemResponse response = new SystemResponse();
			response.setCorrect(false);
			response.setErrors(Arrays.asList(new String[]{npe.getMessage()}));
			return response;
		}
		return null;
	}
	
}
