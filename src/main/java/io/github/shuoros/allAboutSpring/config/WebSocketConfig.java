package io.github.shuoros.allAboutSpring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import io.github.shuoros.allAboutSpring.socket.AssignPrincipalHandshakeHandler;
import io.github.shuoros.allAboutSpring.socket.SocketEndpoints;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker(SocketEndpoints.SUBSCRIBE_QUEUE, SocketEndpoints.SUBSCRIBE_USER_REPLY);
		config.setUserDestinationPrefix(SocketEndpoints.SUBSCRIBE_USER_PREFIX);
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint(SocketEndpoints.ENDPOINT_CONNECT, SocketEndpoints.ENDPOINT_USER)
				.setHandshakeHandler(new AssignPrincipalHandshakeHandler()).setAllowedOriginPatterns("*").withSockJS();
	}

}
