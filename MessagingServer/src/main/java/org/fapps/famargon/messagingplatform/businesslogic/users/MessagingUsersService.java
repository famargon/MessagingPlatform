package org.fapps.famargon.messagingplatform.businesslogic.users;

import org.fapps.famargon.messagingplatform.datamodel.MessagingUser;
import org.fapps.famargon.messagingplatform.datamodel.SystemResponse;
import org.fapps.famargon.messagingplatform.rest.users.RequestObjectUser;

public interface MessagingUsersService {

	SystemResponse saveUser(RequestObjectUser user);
	
	MessagingUser getUser(String id);
	
	boolean deleteUser(String id);
	
	
}
