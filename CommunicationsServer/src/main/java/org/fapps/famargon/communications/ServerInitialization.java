package org.fapps.famargon.communications;

import javax.annotation.PostConstruct;

import org.fapps.famargon.communications.configuration.CommunicationsSystemProperties;
import org.fapps.famargon.communications.datamodel.CommunicationsUser;
import org.fapps.famargon.communications.repository.CommunicationsUserRepository;
import org.fapps.famargon.communications.security.CommunicationsSystemRoles;
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
	private CommunicationsUserRepository userRepository;
	
	@PostConstruct
	public void init(){
		
		JSON_MAPPER = new ObjectMapper();
		JSON_MAPPER.findAndRegisterModules();

		CommunicationsUser adminUser = userRepository.findOneByCommunicationsUser(properties.adminUsername);
		if(adminUser==null){
			adminUser = new CommunicationsUser();
			adminUser.setCommunicationsUser(properties.adminUsername);
			adminUser.setPwd(encoder.encode(properties.adminPassword));
			adminUser.setFullname("Admin user");
			adminUser.setRole(CommunicationsSystemRoles.ADMIN);
			userRepository.save(adminUser);
		}
	}

}
