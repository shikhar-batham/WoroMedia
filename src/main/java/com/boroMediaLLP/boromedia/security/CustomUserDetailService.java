package com.boroMediaLLP.boromedia.security;

import com.boroMediaLLP.boromedia.entity.Users;
import com.boroMediaLLP.boromedia.exception.ResourceNotFoundException;
import com.boroMediaLLP.boromedia.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Users user = this.userRepo.findByEmail(username)
				.orElseThrow(() -> new ResourceNotFoundException("User", "username " + username, 0));

		return user;
	}
}
