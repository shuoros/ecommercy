package io.github.shuoros.allAboutSpring.util;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.github.shuoros.allAboutSpring.Application;

@Service
public class UserDetailService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		return new org.springframework.security.core.userdetails.User(userName,
				Application.getUsers().get(userName), new ArrayList<>());
	}

}
