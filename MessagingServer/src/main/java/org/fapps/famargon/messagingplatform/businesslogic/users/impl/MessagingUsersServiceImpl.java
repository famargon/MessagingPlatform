package org.fapps.famargon.messagingplatform.businesslogic.users.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.fapps.famargon.messagingplatform.businesslogic.users.MessagingUsersService;
import org.fapps.famargon.messagingplatform.datamodel.MessagingUser;
import org.fapps.famargon.messagingplatform.datamodel.SystemPermission;
import org.fapps.famargon.messagingplatform.datamodel.SystemResponse;
import org.fapps.famargon.messagingplatform.repository.UserRepository;
import org.fapps.famargon.messagingplatform.rest.users.RequestObjectUser;
import org.fapps.famargon.messagingplatform.security.MessagingSystemRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MessagingUsersServiceImpl implements MessagingUsersService{

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Secured(MessagingSystemRoles.ADMIN)
	@Override
	public SystemResponse saveUser(RequestObjectUser user) {
		SystemResponse response = new SystemResponse();
		MessagingUser userExists = userRepository.findOneByCommunicationsUser(user.getCommunicationsUser());
		if(userExists!=null){
			response.setCorrect(false);
			response.setRetryable(false);
			response.setErrors(Arrays.asList(new String[]{"CommunicationsUser already exists"}));
			return response;
		}
		MessagingUser communicationsUser = new MessagingUser();
		communicationsUser.setCommunicationsUser(user.getCommunicationsUser());
		communicationsUser.setFullname(user.getFullname());
		communicationsUser.setPwd(encoder.encode(user.getPwd()));
		communicationsUser.setRole(MessagingSystemRoles.USER);
		List<SystemPermission> inboundPermissions = new ArrayList<>();
		inboundPermissions.add(new SystemPermission("PUSH", "/inbound/"+user.getCommunicationsUser()));
		inboundPermissions.add(new SystemPermission("REST", ""));
		List<SystemPermission> outboundPermissions = new ArrayList<>();
		outboundPermissions.add(new SystemPermission("REST", ""));
		communicationsUser.setInboundPermissions(inboundPermissions);
		communicationsUser.setOutboundPermissions(outboundPermissions);
		userRepository.save(communicationsUser);
		response.setCorrect(true);
		response.setEntity(communicationsUser);
		return response;
	}

	@Secured(MessagingSystemRoles.USER)
	@Override
	public MessagingUser getUser(String id) {
		return userRepository.findOneById(id);
	}

	@Secured(MessagingSystemRoles.ADMIN)
	@Override
	public boolean deleteUser(String id) {
		// TODO Auto-generated method stub
		return false;
	}

}
