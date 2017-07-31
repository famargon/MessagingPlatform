package org.fapps.famargon.messagingplatform.repository;

import java.util.List;

import org.fapps.famargon.messagingplatform.datamodel.SystemMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<SystemMessage, String>{

	List<SystemMessage> findByReceivingInstantIsNullAndDestination(String user);
	
	List<SystemMessage> findByReceivingInstantIsNullAndConnectionId(String connectionId);

	
}
