package org.fapps.famargon.messagingplatform;

import javax.annotation.PostConstruct;

import org.fapps.famargon.messagingplatform.configuration.CommunicationsSystemProperties;
import org.fapps.famargon.messagingplatform.datamodel.MessagingUser;
import org.fapps.famargon.messagingplatform.repository.UserRepository;
import org.fapps.famargon.messagingplatform.security.MessagingSystemRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ServerInitialization {
	
	public static ObjectMapper JSON_MAPPER;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private CommunicationsSystemProperties properties;
	@Autowired
	private UserRepository userRepository;

	@PostConstruct
	public void init(){
		
		JSON_MAPPER = new ObjectMapper();
		JSON_MAPPER.findAndRegisterModules();

		MessagingUser adminUser = userRepository.findOneByCommunicationsUser(properties.adminUsername);
		if(adminUser==null){
			adminUser = new MessagingUser();
			adminUser.setCommunicationsUser(properties.adminUsername);
			adminUser.setPwd(encoder.encode(properties.adminPassword));
			adminUser.setFullname("Admin user");
			adminUser.setRole(MessagingSystemRoles.ADMIN);
			userRepository.save(adminUser);
		}
	}

}
