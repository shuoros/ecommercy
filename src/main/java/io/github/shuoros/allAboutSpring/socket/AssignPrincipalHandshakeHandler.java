package io.github.shuoros.allAboutSpring.socket;

import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

public class AssignPrincipalHandshakeHandler extends DefaultHandshakeHandler {
	private static final String ATTR_PRINCIPAL = "__principal__";

	@Override
	protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
			Map<String, Object> attributes) {
		final HttpSession session;
		ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
		session = servletRequest.getServletRequest().getSession();
		attributes.put(ATTR_PRINCIPAL, session.getId());
		return new Principal() {
			@Override
			public String getName() {
				return session.getId();
			}
		};
	}

}
