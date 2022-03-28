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

@RestController
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final Jwt jwtTokenUtil;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, Jwt jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping(value = "/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> authenticate(@RequestBody String payload) {
        final JSONObject request = Request.deserialize(payload);
        if (request == null)
            return Response.error("JSON syntax error!", HttpStatus.BAD_REQUEST).serialize();

        try {
            final String token = authCredentials(request);
            return Response.success("Authentication completed successfully!", token, HttpStatus.OK).serialize();
        } catch (Exception e) {
            return Response.error("Authentication failed!", HttpStatus.UNAUTHORIZED).serialize();
        }
    }

    private String authCredentials(JSONObject json) {
        final Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(//
                        json.getString("email"), //
                        json.getString("password")));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenUtil.generateToken(authentication);
    }

}
