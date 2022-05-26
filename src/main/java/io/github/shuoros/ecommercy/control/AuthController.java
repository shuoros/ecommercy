package io.github.shuoros.ecommercy.control;

import io.github.shuoros.ecommercy.control.util.Request;
import io.github.shuoros.ecommercy.control.util.Response;
import io.github.shuoros.ecommercy.security.jwt.Jwt;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final Jwt jwtTokenUtil;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,//
                          Jwt jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping(value = "/authenticate/admin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> authenticateAdmin(HttpServletRequest req, @RequestBody String payload) {
        final String token = authCredentials(Request.deserialize(payload));
        return Response.success("Authentication completed successfully!", token, HttpStatus.OK).serialize();

    }

    @PostMapping(value = "/authenticate/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> authenticateUser(HttpServletRequest req, @RequestBody String payload) {
        final String token = authCredentials(Request.deserialize(payload));
        return Response.success("Authentication completed successfully!", token, HttpStatus.OK).serialize();
    }

    private String authCredentials(JSONObject json) {
        final Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(//
                        json.getString("username"), //
                        json.getString("password")));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenUtil.generateToken(authentication);
    }

}
