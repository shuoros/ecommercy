package io.github.shuoros.allAboutSpring.util;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.github.shuoros.allAboutSpring.Application;

/**
 * Implements <code>UserDetailsService</code> to locates the user based on the
 * username.
 * 
 * @author Soroush Mehrad
 * @see org.springframework.security.core.userdetails.UserDetailsService
 * @version 1.0.0
 * @since 2021-08-08
 */
@Service
public class UserDetailService implements UserDetailsService {

	/**
	 * Takes a username and returns their password for authentication.
	 * 
	 * @param userName Username of client.
	 * @return org.springframework.security.core.userdetails.User with the given
	 *         username and it's password.
	 * @since v1.0.0
	 */
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		return new org.springframework.security.core.userdetails.User(userName, Application.getUsers().get(userName),
				new ArrayList<>());
	}

}
