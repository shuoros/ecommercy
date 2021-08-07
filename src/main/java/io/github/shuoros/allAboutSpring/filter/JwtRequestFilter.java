package io.github.shuoros.allAboutSpring.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import io.github.shuoros.allAboutSpring.util.JwtUtil;
import io.github.shuoros.allAboutSpring.util.UserDetailService;

public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailService userDetailService;

	@Autowired
	private JwtUtil jwtUtil;

	private static List<String> skipFilterUrls = Arrays.asList("/", "/api/signup", "/authenticate", "/connect/**", "/lib/**");

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

		return skipFilterUrls.stream().anyMatch(url -> new AntPathRequestMatcher(url).matches(request));
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		String authorizationHeader = null;
		try {
			authorizationHeader = Arrays.stream(request.getCookies()).filter(c -> c.getName().equals("JSESSIONTOKEN"))
					.findFirst().map(Cookie::getValue).orElse(null);
		} catch (NullPointerException e) {
		}

		String username = null;
		String jwt = null;

		if (authorizationHeader != null) {
			jwt = authorizationHeader;
			username = jwtUtil.extractUserName(jwt);
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = this.userDetailService.loadUserByUsername(username);

			if (jwtUtil.validateToken(jwt, userDetails)) {

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		chain.doFilter(request, response);
	}
}
