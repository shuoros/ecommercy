package io.github.shuoros.ecommercy.control;

import io.github.shuoros.ecommercy.control.util.Request;
import io.github.shuoros.ecommercy.control.util.Response;
import io.github.shuoros.ecommercy.dao.User;
import io.github.shuoros.ecommercy.dao.service.AdminService;
import io.github.shuoros.ecommercy.dao.service.UserService;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final Jwt jwtTokenUtil;
    private final UserService userService;
    private final AdminService adminService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,//
                          Jwt jwtTokenUtil, UserService userService, AdminService adminService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
        this.adminService = adminService;
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
            return Response.error("Authentication failed! Given email or password may be wrong!", HttpStatus.UNAUTHORIZED).serialize();
        }
    }

    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> signup(@RequestBody String payload) {
        final JSONObject request = Request.deserialize(payload);
        if (request == null)
            return Response.error("JSON syntax error!", HttpStatus.BAD_REQUEST).serialize();

        if (!Request.hasKeys(request, "email", "password", "name"))
            return Response.error("Missed name, email or password!", HttpStatus.UNPROCESSABLE_ENTITY).serialize();

        if (userService.get(request.getString("email")).isPresent()//
                || adminService.get(request.getString("email")).isPresent())
            return Response.error("User with such email already exists!", HttpStatus.CONFLICT).serialize();

        try {
            userService.create(User.builder()//
                    .name(request.getString("name"))//
                    .email(request.getString("email"))//
                    .password(new BCryptPasswordEncoder().encode(request.getString("password"))).build());
            return Response.success("Signup completed successfully!", HttpStatus.OK).serialize();
        } catch (Exception e) {
            return Response.error("Signup failed!", HttpStatus.INTERNAL_SERVER_ERROR).serialize();
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
