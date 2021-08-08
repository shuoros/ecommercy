package io.github.shuoros.allAboutSpring.controller;

import java.lang.invoke.MethodHandles;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.shuoros.allAboutSpring.Application;
import io.github.shuoros.allAboutSpring.model.User;
import io.github.shuoros.allAboutSpring.util.PasswordEncoder;

/**
 * This class is the controller of Post requests to the server. This controller
 * listens to requests with the "/api" prefix and delivers them to the
 * corresponding implemented function. If it does not find the requested path,
 * it returns a 404 error.
 * 
 * @author Soroush Mehrad
 * @version 1.0.1
 * @since 2021-08-08
 */
@RestController
@RequestMapping("/api")
public class APIController {

	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	/**
	 * This function receives Post requests on the "/signup" path and its task is to
	 * receive the username and password from the client. If the received username
	 * exists in the <code>Application.users</code>, error 400 returns and notifies
	 * the client to enter another username. If there is no username, the received
	 * password will be hashed by <code>PasswordEncoder</code> class and stored with
	 * the username as a new user and return 200 to client.
	 * 
	 * @param jwt     The JWT that stores in client Cookie to Authorize client.
	 * @param request Client's device info.
	 * @param payload Client's message that contains username and password.
	 * @return A <code>ResponseEntity</code> that contains a string and http
	 *         status code.
	 * @version 1.0.1
	 */
	@PostMapping(path = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createAdmin(@CookieValue(value = "JSESSIONTOKEN", defaultValue = "null") String jwt,
			HttpServletRequest request, @RequestBody String payload) {
		log.info("<=== handleSingInAPI: session=" + jwt + ", payload=" + payload + " ip=" + request.getRemoteAddr()
				+ ", user-agent=" + request.getHeader("User-Agent"));
		JSONObject data = new JSONObject(payload);
		JSONObject response = new JSONObject();
		/**
		 * Checks if name is present in payload.
		 * 
		 * @since 1.0.1
		 */
		if (!data.has("name")) {
			response.put("ok", false);
			response.put("status", 400);
			response.put("message", "Name is missing");
			return new ResponseEntity<String>(response.toString(), HttpStatus.BAD_REQUEST);
		}
		/**
		 * Checks if password is present in payload.
		 * 
		 * @since 1.0.1
		 */
		if (!data.has("password")) {
			response.put("ok", false);
			response.put("status", 400);
			response.put("message", "Password is missing");
			return new ResponseEntity<String>(response.toString(), HttpStatus.BAD_REQUEST);
		}
		/**
		 * Change the letters of the name to lowercase letters so that the username
		 * become non-case sensitive.
		 * 
		 * @since 1.0.0
		 */
		data.put("name", data.getString("name").toLowerCase());
		/**
		 * If a user with same username exists before return a 400 error.
		 * 
		 * @since 1.0.0
		 */
		if (Application.getUsers().containsKey(data.getString("name"))) {
			response.put("ok", false);
			response.put("status", 400);
			response.put("message", "A user with this name exist!");
			return new ResponseEntity<String>(response.toString(), HttpStatus.BAD_REQUEST);
		}
		User user;
		/**
		 * Hash the users password.
		 * 
		 * @see io.github.shuoros.allAboutSpring.util.PasswordEncoder
		 * @since 1.0.0
		 */
		try {
			user = new User(data.getString("name"),
					new PasswordEncoder(data.getString("password"), data.getString("name")).encode());
		} catch (JSONException | InvalidKeySpecException | NoSuchAlgorithmException e) {
			e.printStackTrace();
			response.put("ok", false);
			response.put("status", 500);
			response.put("message", "Something wents wrong! Contact support.");
			return new ResponseEntity<String>(response.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		/**
		 * Save the new user in the <code>Application.users</code>.
		 * 
		 * @since 1.0.0
		 */
		Application.addUser(user.getName(), user.getPassword());
		response.put("ok", true);
		response.put("status", 200);
		response.put("message", "User added");
		return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
	}
}
