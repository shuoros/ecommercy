package io.github.shuoros.ecommercy;

import io.github.shuoros.ecommercy.aspect.AuthControllerAspects;
import io.github.shuoros.ecommercy.config.AspectJConfig;
import io.github.shuoros.ecommercy.config.OpenApiConfig;
import io.github.shuoros.ecommercy.control.AuthController;
import io.github.shuoros.ecommercy.control.ExceptionController;
import io.github.shuoros.ecommercy.security.SecurityConfig;
import io.github.shuoros.ecommercy.security.UnauthorizedEntryPoint;
import io.github.shuoros.ecommercy.security.filter.ExceptionHandlerFilter;
import io.github.shuoros.ecommercy.security.filter.JwtAuthenticationFilter;
import io.github.shuoros.ecommercy.security.filter.RequestsLoggerFilter;
import io.github.shuoros.ecommercy.security.filter.RestDataSecurityFilter;
import io.github.shuoros.ecommercy.security.jwt.Jwt;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class EcommercyApplicationTests {

    @Autowired
    private EcommercyApplication application;

    @Autowired
    private AuthController authController;

    @Autowired
    private AuthControllerAspects authControllerAspects;

    @Autowired
    private ExceptionController exceptionController;

    @Autowired
    private AspectJConfig aspectJConfig;

    @Autowired
    private OpenApiConfig openApiConfig;

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private UnauthorizedEntryPoint unauthorizedEntryPoint;

    @Resource(name = "userDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private Jwt jwt;

    @Autowired
    private ExceptionHandlerFilter exceptionHandlerFilter;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private RequestsLoggerFilter requestsLoggerFilter;

    @Autowired
    private RestDataSecurityFilter restDataSecurityFilter;

    @Test
    void contextLoads() {
        assertNotNull(application);
    }

    @Test
    void authControllerLoads() {
        assertNotNull(authController);
    }

    @Test
    void authControllerAspectsLoads() {
        assertNotNull(authControllerAspects);
    }

    @Test
    void exceptionControllerLoads() {
        assertNotNull(exceptionController);
    }

    @Test
    void aspectJConfigLoads() {
        assertNotNull(aspectJConfig);
    }

    @Test
    void openApiConfigLoads() {
        assertNotNull(openApiConfig);
    }

    @Test
    void securityConfigLoads() {
        assertNotNull(securityConfig);
    }

    @Test
    void unauthorizedEntryPointLoads() {
        assertNotNull(unauthorizedEntryPoint);
    }

    @Test
    void userDetailsServiceLoads() {
        assertNotNull(userDetailsService);
    }

    @Test
    void jwtLoads() {
        assertNotNull(jwt);
    }

    @Test
    void exceptionHandlerFilterLoads() {
        assertNotNull(exceptionHandlerFilter);
    }

    @Test
    void jwtAuthenticationFilterLoads() {
        assertNotNull(jwtAuthenticationFilter);
    }

    @Test
    void requestsLoggerFilterLoads() {
        assertNotNull(requestsLoggerFilter);
    }

    @Test
    void restDataSecurityFilterLoads() {
        assertNotNull(restDataSecurityFilter);
    }

}
