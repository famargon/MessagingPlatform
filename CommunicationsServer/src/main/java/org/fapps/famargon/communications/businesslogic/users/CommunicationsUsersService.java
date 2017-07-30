package org.fapps.famargon.communications.businesslogic.users;

import org.fapps.famargon.communications.datamodel.CommunicationsUser;
import org.fapps.famargon.communications.datamodel.SystemResponse;
import org.fapps.famargon.communications.rest.users.RequestObjectUser;

public interface CommunicationsUsersService {

	SystemResponse saveUser(RequestObjectUser user);
	
	CommunicationsUser getUser(String id);
	
	boolean deleteUser(String id);
	
	
}
