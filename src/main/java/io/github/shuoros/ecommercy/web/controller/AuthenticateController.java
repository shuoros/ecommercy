package io.github.shuoros.ecommercy.web.controller;

import io.github.shuoros.ecommercy.model.model.JWT;
import io.github.shuoros.ecommercy.model.vm.LoginVM;
import io.github.shuoros.ecommercy.model.vm.RegisterVM;
import io.github.shuoros.ecommercy.security.jwt.JWTProvider;
import io.github.shuoros.ecommercy.service.CustomerService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class AuthenticateController {

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    private CustomerService customerService;

    @PostMapping("/authenticate")
    public ResponseEntity<JWT> authenticate(@Valid @RequestBody LoginVM loginVM) {
        log.debug("request to authenticate: {}", loginVM);

        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginVM.getUsername(),
                loginVM.getPassword()
        );

        final Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String jwt = jwtProvider.generateToken(authentication, loginVM.isRememberMe());

        return ResponseEntity.ok(
                JWT.builder().token(jwt).build()
        );
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAccount(@Valid @RequestBody RegisterVM registerVM) {
        log.debug("request to register new customer: {}", registerVM);

        customerService.register(registerVM);
    }
}
