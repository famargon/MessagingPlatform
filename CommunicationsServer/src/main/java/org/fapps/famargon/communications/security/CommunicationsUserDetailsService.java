package org.fapps.famargon.communications.security;

import org.fapps.famargon.communications.repository.CommunicationsUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CommunicationsUserDetailsService implements UserDetailsService {

	@Autowired
	private CommunicationsUserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails user = userRepository.findOneByCommunicationsUser(username);
		if(user==null){
			throw new UsernameNotFoundException("Not found username with name "+username);
		}
		return user;
	}

}
