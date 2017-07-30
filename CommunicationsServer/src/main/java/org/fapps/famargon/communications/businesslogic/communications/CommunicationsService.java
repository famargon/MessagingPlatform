package org.fapps.famargon.communications.businesslogic.communications;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.fapps.famargon.communications.SystemUtils;
import org.fapps.famargon.communications.businesslogic.validator.CommunicationsEntitiesValidator;
import org.fapps.famargon.communications.datamodel.SystemMessage;
import org.fapps.famargon.communications.datamodel.SystemResponse;
import org.fapps.famargon.communications.messagesengine.InboundQueue;
import org.fapps.famargon.communications.messagesengine.OutboundSystem;
import org.fapps.famargon.communications.repository.MessageRepository;
import org.fapps.famargon.communications.security.CommunicationsSystemRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

@Service
public class CommunicationsService {
	
	@Autowired
	MessageRepository messageRepository;
	@Autowired
	OutboundSystem outboundQueue;
	@Autowired 
	InboundQueue inboundQueue;  
	@Autowired 
	CommunicationsEntitiesValidator validator;
	
	/**
	 * Este método solo será invocado por Rest en la parte Java y su presencia es temporal,
	 * el objetivo de este sistema es que todos los mensajes que se envian pasen por la cola
	 * @param message
	 * @return
	 */
	@Secured(CommunicationsSystemRoles.USER)
	public SystemResponse directSending(SystemMessage message){
		SystemResponse response = validator.validateSystemEntry(message);
		if(response!=null){
			return response;
		}
		message.setSystemEntryInstant(Instant.now());
		message.setConnectionId(UUID.randomUUID().toString());
		send(message);
		response = new SystemResponse();
		response.setConnectionId(message.getConnectionId());
		response.setSystemEntryInstant(message.getSystemEntryInstant());
		response.setCorrect(true);
		return response;
	}
	
	/**
	 * Este método implementa lo que se debe implementar en la parte nodejs
	 * Por las carácterísticas de nodejs es mas eficiente que esa parte añada a la cola los mensajes
	 * y la parte java los vaya consumiendo a su llegada
	 * El mensaje no se guarda en bbdd hasta que no entra en la cola
	 * 
	 * TODO migrar a nodejs
	 * 
	 * @param message
	 * @return
	 */
	@Secured(CommunicationsSystemRoles.USER)
	public SystemResponse systemSend(SystemMessage message){
		SystemResponse response = validator.validateSystemEntry(message);
		if(response!=null){
			return response;
		}
		message.setSystemEntryInstant(Instant.now());
		message.setConnectionId(UUID.randomUUID().toString());
		response = new SystemResponse();
		String jsonMessage = SystemUtils.objectToJson(message);
		if(StringUtils.isNotBlank(jsonMessage) && inboundQueue.getInboundQueue().offer(jsonMessage)){
			response.setConnectionId(message.getConnectionId());
			response.setSystemEntryInstant(message.getSystemEntryInstant());
			response.setCorrect(true);
		}else{
			response.setErrors(Arrays.asList(new String[]{"Message cannot be add to sending queue"}));
			response.setCorrect(false);
			response.setRetryable(true);
		}
		return response;
	}

	public void send(SystemMessage message){
		if(message.getSystemEntryInstant()==null){
			message.setSystemEntryInstant(Instant.now());
		}
		message.setSendingInstant(Instant.now());
		messageRepository.save(message);
		outboundQueue.pushMessage(message);
	}

	@Secured(CommunicationsSystemRoles.USER)
	public List<SystemMessage> receiveByUser(String user){
		return messageRepository.findByReceivingInstantIsNullAndDestination(user)
				.stream()
				.map(message -> { 
					receive(message);
					return message;})
				.collect(Collectors.toList());
		
	}
	
	@Secured(CommunicationsSystemRoles.USER)
	public List<SystemMessage> receiveByConnectionId(String connectionId){
		return messageRepository.findByReceivingInstantIsNullAndConnectionId(connectionId)
				.stream()
				.map(message -> {
					receive(message);
					return message;
				})
				.collect(Collectors.toList());
	}
	
	private void receive(SystemMessage message){
		message.setReceivingInstant(Instant.now());
		messageRepository.save(message);
	}
}
