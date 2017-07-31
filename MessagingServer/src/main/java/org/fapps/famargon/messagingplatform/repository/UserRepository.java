package org.fapps.famargon.messagingplatform.repository;

import org.fapps.famargon.messagingplatform.datamodel.MessagingUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<MessagingUser, String>{

	MessagingUser findOneByCommunicationsUser(String communicationsUser);
	
	MessagingUser findOneById(String id);

}
