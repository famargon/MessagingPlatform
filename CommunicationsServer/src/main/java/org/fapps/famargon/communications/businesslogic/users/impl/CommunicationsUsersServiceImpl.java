package org.fapps.famargon.communications.businesslogic.users.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.fapps.famargon.communications.businesslogic.users.CommunicationsUsersService;
import org.fapps.famargon.communications.datamodel.CommunicationsPermission;
import org.fapps.famargon.communications.datamodel.CommunicationsUser;
import org.fapps.famargon.communications.datamodel.SystemResponse;
import org.fapps.famargon.communications.repository.CommunicationsUserRepository;
import org.fapps.famargon.communications.rest.users.RequestObjectUser;
import org.fapps.famargon.communications.security.CommunicationsSystemRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CommunicationsUsersServiceImpl implements CommunicationsUsersService{

	@Autowired
	private CommunicationsUserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Secured(CommunicationsSystemRoles.ADMIN)
	@Override
	public SystemResponse saveUser(RequestObjectUser user) {
		SystemResponse response = new SystemResponse();
		CommunicationsUser userExists = userRepository.findOneByCommunicationsUser(user.getCommunicationsUser());
		if(userExists!=null){
			response.setCorrect(false);
			response.setRetryable(false);
			response.setErrors(Arrays.asList(new String[]{"CommunicationsUser already exists"}));
			return response;
		}
		CommunicationsUser communicationsUser = new CommunicationsUser();
		communicationsUser.setCommunicationsUser(user.getCommunicationsUser());
		communicationsUser.setFullname(user.getFullname());
		communicationsUser.setPwd(encoder.encode(user.getPwd()));
		communicationsUser.setRole(CommunicationsSystemRoles.USER);
		List<CommunicationsPermission> inboundPermissions = new ArrayList<>();
		inboundPermissions.add(new CommunicationsPermission("PUSH", "/inbound/"+user.getCommunicationsUser()));
		inboundPermissions.add(new CommunicationsPermission("REST", ""));
		List<CommunicationsPermission> outboundPermissions = new ArrayList<>();
		outboundPermissions.add(new CommunicationsPermission("REST", ""));
		communicationsUser.setInboundPermissions(inboundPermissions);
		communicationsUser.setOutboundPermissions(outboundPermissions);
		userRepository.save(communicationsUser);
		response.setCorrect(true);
		response.setEntity(communicationsUser);
		return response;
	}

	@Secured(CommunicationsSystemRoles.USER)
	@Override
	public CommunicationsUser getUser(String id) {
		return userRepository.findOneById(id);
	}

	@Secured(CommunicationsSystemRoles.ADMIN)
	@Override
	public boolean deleteUser(String id) {
		// TODO Auto-generated method stub
		return false;
	}

}
