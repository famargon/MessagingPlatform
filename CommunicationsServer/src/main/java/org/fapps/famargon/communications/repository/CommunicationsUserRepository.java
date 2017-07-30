package org.fapps.famargon.communications.repository;

import org.fapps.famargon.communications.datamodel.CommunicationsUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommunicationsUserRepository extends MongoRepository<CommunicationsUser, String>{

	CommunicationsUser findOneByCommunicationsUser(String communicationsUser);
	
	CommunicationsUser findOneById(String id);

}
