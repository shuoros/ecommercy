# All about Spring
### This repository contains all the knowledge I have and all the things I can do with Spring
In this repository, a spring boot application has been implemented that has a registration and login system. After logging in, you can use a public chat system that sends your message to all online sessions. You can also view and reply to messages from other sessions.

**The online users' box system and private chat will be implemented in the next version.**
## Functionalities that implemented in version one
- Spring Security
- JWT Authorization
- Spring Rest Service
- Thymleaf
- Spring WebSocket
- Spring Session

## In this section, you can read the instruction of each of the implemented items
### Spring Security and JWT Authorization
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

### Spring Rest Service
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


### Thymleaf
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

### Spring WebSocket and Session
Readme is not complete yet...
