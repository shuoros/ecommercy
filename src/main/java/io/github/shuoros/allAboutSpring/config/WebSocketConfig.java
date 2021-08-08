package io.github.shuoros.allAboutSpring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import io.github.shuoros.allAboutSpring.socket.AssignPrincipalHandshakeHandler;
import io.github.shuoros.allAboutSpring.socket.SocketEndpoints;

/**
 * Enables the WebSocket capabilities. As its name suggests, it enables
 * WebSocket message handling, backed by a message broker.
 * 
 * @author Soroush Mehrad
 * @version 1.0.0
 * @since 2021-08-08
 */
@SuppressWarnings("deprecation")
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

	/**
	 * Here, we can see that the method configureMessageBroker is used to configure
	 * the message broker. First, we enable an in-memory message broker to carry the
	 * messages back to the client on destinations prefixed with "/reply". The
	 * "/queue" prefix is for public messaging. The messages that the server puts on
	 * this path are sent to all the clients that are subscribed on this path.
	 * 
	 * @since v1.0.0
	 */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker(SocketEndpoints.SUBSCRIBE_QUEUE, SocketEndpoints.SUBSCRIBE_USER_REPLY);
		config.setUserDestinationPrefix(SocketEndpoints.SUBSCRIBE_USER_PREFIX);
	}

	/**
	 * We complete our configuration by designating the "/private" prefix to filter
	 * destinations targeting application annotated methods (via
	 * <code>@MessageMapping</code>). The registerStompEndpoints method registers
	 * the "/connect" and "/user" endpoint, enabling Spring’s STOMP support. It also
	 * enables the SockJS fallback options, so that alternative messaging options
	 * may be used if WebSockets are not available. This is useful since WebSocket
	 * is not supported in all browsers yet and may be precluded by restrictive
	 * network proxies. The fallbacks let the applications use a WebSocket API but
	 * gracefully degrade to non-WebSocket alternatives when necessary at runtime.
	 * 
	 * @since v1.0.0
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint(SocketEndpoints.ENDPOINT_CONNECT, SocketEndpoints.ENDPOINT_USER)
				.setHandshakeHandler(new AssignPrincipalHandshakeHandler()).setAllowedOriginPatterns("*").withSockJS();
	}

}
