# All about Spring
### This repository contains all the knowledge I have and all the things I can do with Spring
In this repository, a spring boot application has been implemented that has a registration and login system. After logging in, you can use a public chat system that sends your message to all online sessions. You can also view and reply to messages from other sessions.

**The online users' box system and private chat will be implemented in the next version.**
## Functionalities that implemented in version one
- [Spring Security](https://github.com/shuoros/all-about-spring#spring-security-and-jwt-authorization)
- [JWT Authorization](https://github.com/shuoros/all-about-spring#spring-security-and-jwt-authorization)
- [Spring Rest Service](https://github.com/shuoros/all-about-spring#spring-rest-service)
- [Thymleaf](https://github.com/shuoros/all-about-spring#thymleaf)
- [Spring WebSocket](https://github.com/shuoros/all-about-spring#spring-websocket-and-session)
- [Spring Session](https://github.com/shuoros/all-about-spring#spring-websocket-and-session)

## Spring Security and JWT Authorization
To implement Spring Security settings, the `SecurityConfig extends WebSecurityConfigurerAdapter` and it's filter `JwtRequestFilter extends OncePerRequestFilter` is required to implement.

**(Security settings can also be done via the XML file, You can read [docs](https://spring.io/projects/spring-security) or [this tutorial](https://www.baeldung.com/spring-security-basic-authentication))**

The settings I have done for my application are such that all the requests that reach the server hit a filter before the controllers. If they are authenticated by jwt, they reach the controllers, otherwise the code 403 is returned to them.
```java
@Override
protected void configure(HttpSecurity http) throws Exception {
	http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().exceptionHandling().and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
}
```

Paths defined in the 
```java
@Override
public void configure(WebSecurity web) throws Exception {
	web.ignoring().antMatchers("/", "/api/signup", "/authenticate", "/connect/**", "/lib/**");
}
```
in the `SecurityConfig` class and the
```java
private static List<String> skipFilterUrls = Arrays.asList("/", "/api/signup", "/authenticate", "/connect/**", "/lib/**");

@Override
protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
	return skipFilterUrls.stream().anyMatch(url -> new AntPathRequestMatcher(url).matches(request));
}
```
in the `JwtRequestFilter` are ignored for authentication and hits the controllers.
- `"/"` is for index page.
- `"/api/signup"` is for signup POST request.
- `"/authenticate"` is a POST path for authenticate users and assign jwt to them.
- `"/connect/**"` is for WebSocket.
- `"/lib/**"` is for Stomp client js files.

## Spring Rest Service
To implement a reset service system, a class must be implemented as a controller and receive requests for a URL path using `@PostMapping(path = "<PATH>", produces = MediaType.APPLICATION_JSON_VALUE)` annotation.
```java
@RestController
@RequestMapping("/api")
public class APIController {

	@PostMapping(path = "<PATH>", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> signup(@CookieValue(value = "JSESSIONTOKEN", defaultValue = "null") String jwt,
			HttpServletRequest request, @RequestBody String payload) {
		return new ResponseEntity<String>(<RESPONSE>, HttpStatus.OK);
	}
  
}
```
- `@RestController` and `@RequestMapping("<PRIFIX>")` are annotions for Rest controller's class. `<PRIFIX>` Is a path that comes before api path's.
-  `@CookieValue(value = "JSESSIONTOKEN", defaultValue = "null") String` jwt Is for JWT in client Cookie.
-  `HttpServletRequest request` Is for client Information.
-  `@RequestBody String payload` Is the body of request.
-  Returns `new ResponseEntity<String>(<RESPONSE>, HttpStatus.OK)` as response to client. `<RESPONSE>` Is an object that returns to client. `HttpStatus.OK` the status code of response to client.


## Thymleaf
To implement a reset service system, a class must be implemented as a controller and receive requests for a URL path using `@RequestMapping(<PATH>)` anotation.
```java
@Controller
public class ResourceController {

	@RequestMapping("/")
	public String index(@CookieValue(value = "JSESSIONTOKEN", defaultValue = "null") String jwt,
			HttpServletRequest request, Model model) {
		return "index";
	}

}
```
-  `@CookieValue(value = "JSESSIONTOKEN", defaultValue = "null") String` jwt Is for JWT in client Cookie.
-  `HttpServletRequest request` Is for client Information.
-  `Model model` Is for Thymleaf template engine.
-  Returns name of html file palced in `src/main/resources/templates/` that we want to return to the client.

## Spring WebSocket and Session
### Server side
The first thing to do is to enable the WebSocket capabilities. To do this we need to add a configuration to our application and annotate this class with `@EnableWebSocketMessageBroker`.

As its name suggests, it enables WebSocket message handling, backed by a message broker:
```java
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
```

I implement the constatnts in SocketEndpoints class:
```java
public class SocketEndpoints {
	public static final String SUBSCRIBE_USER_PREFIX = "/private";
	public static final String SUBSCRIBE_USER_REPLY = "/reply";
	public static final String SUBSCRIBE_QUEUE = "/queue";
	public static final String ENDPOINT_CONNECT = "/connect";
	public static final String ENDPOINT_USER = "/user";
}
```

Here, we can see that the method `configureMessageBroker` is used to configure the message broker. First, we enable an in-memory message broker to carry the messages back to the client on destinations prefixed with `"/reply"`.

The `"/queue"` prefix is for public messaging. The messages that the server puts on this path are sent to all the clients that are subscribed on this path.

We complete our configuration by designating the `"/private"` prefix to filter destinations targeting application annotated methods (via `@MessageMapping`).
The `registerStompEndpoints` method registers the `"/connect"` and `"/user"` endpoint, enabling **Spring’s STOMP support**.

It also **enables the SockJS fallback options**, so that alternative messaging options may be used if WebSockets are not available. This is useful since WebSocket is not supported in all browsers yet and may be precluded by restrictive network proxies.

The fallbacks let the applications use a WebSocket API but gracefully degrade to non-WebSocket alternatives when necessary at runtime.

To assign a session to each client, A hand shake handler implement in `AssignPrincipalHandshakeHandler` class, That gives a session as `JSESSIONID` in client's Cookie before the web socket establish between the client and server.

```java
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
```

To handle web socket events, a `EventHandler` class is imlemented that listens to we socket events using `@EventListener` annotation to log them.
```java
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
```
As I said above, To send a message through the web socket, There are two ways of private and public sending, Which private way is for send messages to a specific session, and public way is for sending messages to all sessions that have subscribed.

With the `convertAndSend` method, the message is sent to an endpoint that has been seted as the public message path (in my code is `"/queue"`) and all sessions that subscribe to this endpoint will receive it.
```java
messagingTemplate.convertAndSend(SocketEndpoints.SUBSCRIBE_QUEUE + "/public", <RESPONSE>);
```

The `convertAndSendToUser` method is for sending a private message to a specific session and gets the desired client session and the predefined private message path and the message as arguments.
```java
messagingTemplate.convertAndSendToUser(session, Constants.SUBSCRIBE_USER_REPLY, <RESPONSE>);
```

### Client side
First of all, you need to provide the web socket and stomp libraries for the client. [download here](https://github.com/shuoros/all-about-spring/raw/main/js%20websocket%20libs.zip)

In the following code snippet, the client first requests a connection to the specified path for the web socket, which is the `"/connect"`, And if a connection is established, Stomp protocol sits on the connection. Finally, The client subscribes to private and public message paths and pass incoming messages to the `responseHandler` function to be handled.
```javascript
window.onload = function() {
	var socket = new SockJS(location.protocol + "//" + location.host
        	+ "/connect");
        stompClient = Stomp.over(socket);
        stompClient.debug = null;
        stompClient.connect({}, function(frame) {
        	stompClient.subscribe('/private/reply', function(notification) {
                	responseHandler(notification.body);
                });
                stompClient.subscribe('/queue/public', function(notification) {
                        responseHandler(notification.body);
                });
         });
}

function responseHandler(data) {
	console.log(data);
}
```
