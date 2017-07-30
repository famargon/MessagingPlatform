package org.fapps.famargon.communications.repository;

import java.util.List;

import org.fapps.famargon.communications.datamodel.SystemMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<SystemMessage, String>{

	List<SystemMessage> findByReceivingInstantIsNullAndDestination(String user);
	
	List<SystemMessage> findByReceivingInstantIsNullAndConnectionId(String connectionId);

	
}
