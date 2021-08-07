package io.github.shuoros.allAboutSpring.socket;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Configuration
public class EventHandler {

	private static Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@EventListener
	public void handleSubscribeEvent(SessionSubscribeEvent event) {
		log.info("<==> handleSubscribeEvent: session=" + event.getUser().getName() + ", event=" + event);
	}

	@EventListener
	public void handleConnectEvent(SessionConnectEvent event) {
		log.info("===> handleConnectEvent: session=" + event.getUser().getName() + ", event=" + event);
	}

	@EventListener
	public void handleDisconnectEvent(SessionDisconnectEvent event) {
		log.info("<=== handleDisconnectEvent: session=" + event.getUser().getName() + ", event=" + event);
	}
}
