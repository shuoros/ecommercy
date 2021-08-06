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

@RestController
@RequestMapping("/api")
public class APIController {

	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@PostMapping(path = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createAdmin(@CookieValue(value = "JSESSIONTOKEN", defaultValue = "null") String jwt,
			HttpServletRequest request, @RequestBody String payload) {
		log.info("<=== handleSingInAPI: session=" + jwt + ", payload=" + payload + " ip=" + request.getRemoteAddr()
				+ ", user-agent=" + request.getHeader("User-Agent"));
		JSONObject data = new JSONObject(payload);
		data.put("name", data.getString("name").toLowerCase());
		JSONObject response = new JSONObject();
		if (Application.getUsers().containsKey(data.getString("name"))) {
			response.put("ok", false);
			response.put("status", 400);
			response.put("message", "A user with this name exist!");
			return new ResponseEntity<String>(response.toString(), HttpStatus.BAD_REQUEST);
		}
		User user;
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
		Application.addUser(user.getName(), user.getPassword());
		response.put("ok", true);
		response.put("status", 200);
		response.put("message", "User added");
		return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
	}
}
