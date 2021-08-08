package io.github.shuoros.allAboutSpring.controller;

import java.lang.invoke.MethodHandles;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.github.shuoros.allAboutSpring.Application;
import io.github.shuoros.allAboutSpring.util.JwtUtil;
import io.github.shuoros.allAboutSpring.util.PasswordEncoder;
import io.github.shuoros.allAboutSpring.util.UserDetailService;

/**
 * This class is responsible for user authentication and is an interface for the
 * functions and settings implemented
 * {@link io.github.shuoros.allAboutSpring.util.UserDetailService} and
 * {@link io.github.shuoros.allAboutSpring.config.SecurityConfig}
 * 
 * @author Soroush Mehrad
 * @version 1.0.0
 * @since 2021-08-08
 */
@Controller
public class AuthenticateController {

	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private UserDetailService userDetailService;

	/**
	 * It uses <code>authenticationManager</code> to authenticate the user by its
	 * username and password. If the given username and password are incorrect, a
	 * <code>BadCredentialsException</code> will be throwed. If the username and
	 * password are correct, a JWT will be created for the client and returned to
	 * it.
	 * 
	 * @param request Client's device info.
	 * @param payload Client's message that contains username and password.
	 * @return A <code>ResponseEntity</code> that contains a string and http
	 *         status code.
	 * @throws Exception {@link org.springframework.security.authentication.BadCredentialsException}
	 * @since v1.0.0
	 */
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> authenticate(HttpServletRequest request, @RequestBody String payload) throws Exception {
		JSONObject data = new JSONObject(payload);
		data.put("name", data.getString("name").toLowerCase());
		/**
		 * Checks if user exists.
		 * 
		 * @since v1.0.0
		 */
		if (Application.getUsers().containsKey(data.getString("name"))) {
			log.info("<=== handleAuthenticateAPI: username=" + data.getString("name") + " password="
					+ data.getString("password") + " ip=" + request.getRemoteAddr() + ", user agent="
					+ request.getHeader("User-Agent"));
			/**
			 * Authenticate user with username and password.
			 * 
			 * @since v1.0.0
			 */
			try {
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(data.getString("name"),
						new PasswordEncoder(data.getString("password"), data.getString("name")).encode()));
			} catch (BadCredentialsException e) {
				JSONObject response = new JSONObject();
				response.put("ok", false);
				response.put("status", 403);
				response.put("message", "Username or Password is wrong!");
				return new ResponseEntity<String>(response.toString(), HttpStatus.FORBIDDEN);
			}

			final UserDetails userDetails = userDetailService.loadUserByUsername(data.getString("name"));

			String jwt;
			/**
			 * Generate JWT for client.
			 * 
			 * @since v1.0.0
			 */
			if (data.getBoolean("remember")) {
				jwt = jwtTokenUtil.generateInfToken(userDetails);
			} else {
				jwt = jwtTokenUtil.generateToken(userDetails);
			}

			JSONObject response = new JSONObject();
			response.put("ok", true);
			response.put("status", 200);
			response.put("message", "Authemticated successfully");
			response.put("jwt", jwt);
			return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
		}

		JSONObject response = new JSONObject();
		response.put("ok", false);
		response.put("status", 400);
		response.put("message", "User not found!");
		return new ResponseEntity<String>(response.toString(), HttpStatus.BAD_REQUEST);
	}
}
