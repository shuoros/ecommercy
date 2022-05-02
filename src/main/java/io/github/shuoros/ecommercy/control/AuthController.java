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

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final Jwt jwtTokenUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;
    private final AdminService adminService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,//
                          Jwt jwtTokenUtil, BCryptPasswordEncoder bCryptPasswordEncoder,//
                          UserService userService, AdminService adminService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
        this.adminService = adminService;
    }

    @PostMapping(value = "/authenticate/admin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> authenticateAdmin(HttpServletRequest req, @RequestBody String payload) {
        final JSONObject request = Request.deserialize(payload);
        final String token = authCredentials(request);
        return Response.success("Authentication completed successfully!", token, HttpStatus.OK).serialize();

    }

    @PostMapping(value = "/authenticate/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> authenticateUser(HttpServletRequest req, @RequestBody String payload) {
        final JSONObject request = Request.deserialize(payload);
        final String token = authCredentials(request);
        return Response.success("Authentication completed successfully!", token, HttpStatus.OK).serialize();
    }

    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> signup(HttpServletRequest req, @RequestBody String payload) {
        final JSONObject request = Request.deserialize(payload);
        try {
            userService.create(User.builder()//
                    .name(request.getString("name"))//
                    .email(request.getString("email"))//
                    .password(bCryptPasswordEncoder.encode(request.getString("password"))).build());
            return Response.success("Signup completed successfully!", HttpStatus.OK).serialize();
        } catch (Exception e) {
            return Response.error("Signup failed!", HttpStatus.INTERNAL_SERVER_ERROR).serialize();
        }
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
