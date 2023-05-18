package com.infodev.sanimaotp.configurations;

import com.infodev.sanimaotp.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service(value="getUserDetails")
public class UserSecurityService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		return Optional.ofNullable(userRepository.findByName(username))
				.orElseThrow(()-> new UsernameNotFoundException("Username not found"));

	}

}
