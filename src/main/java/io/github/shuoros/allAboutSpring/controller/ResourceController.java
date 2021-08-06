package io.github.shuoros.allAboutSpring.controller;

import java.lang.invoke.MethodHandles;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import io.github.shuoros.allAboutSpring.util.JwtUtil;

@Controller
public class ResourceController {

	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	private JwtUtil jwtUtil;

	@RequestMapping("/")
	public String index(@CookieValue(value = "JSESSIONTOKEN", defaultValue = "null") String jwt,
			HttpServletRequest request, @RequestHeader String host, Model model) {
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
