package io.github.shuoros.allAboutSpring.controller;

import java.lang.invoke.MethodHandles;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import io.github.shuoros.allAboutSpring.util.JwtUtil;

/**
 * This class is the Thymleaf template engine Controller and is responsible for
 * listening to client requests for web pages and returning those pages to the
 * user.
 * 
 * @author Soroush Mehrad
 * @version 1.0.0
 * @since 2021-08-08
 */
@Controller
public class ThymleafController {

	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	private JwtUtil jwtUtil;

	/**
	 * This function is responsible for returning the HTML page to the "/" path.
	 * 
	 * @param jwt     The JWT that stores in client Cookie to Authorize client.
	 * @param request Client's device info.
	 * @param model   {@link org.springframework.ui.Model}
	 * @return Name of HTML file for this path.
	 * @since v1.0.0
	 */
	@RequestMapping("/")
	public String index(@CookieValue(value = "JSESSIONTOKEN", defaultValue = "null") String jwt,
			HttpServletRequest request, Model model) {
		log.info("<=== handleIndexResource: session=" + jwt + ", ip=" + request.getRemoteAddr() + ", user-agent="
				+ request.getHeader("User-Agent"));
		if (jwt.equals("null") || jwtUtil.isTokenExpired(jwt)) {
			model.addAttribute("login", false);
			model.addAttribute("name", "stranger");
		} else {
			model.addAttribute("login", true);
			model.addAttribute("name", jwtUtil.extractUserName(jwt));
		}
		return "index";
	}

}
