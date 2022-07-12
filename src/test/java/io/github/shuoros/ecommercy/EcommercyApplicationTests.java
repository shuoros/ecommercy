package io.github.shuoros.ecommercy;

import io.github.shuoros.ecommercy.aspect.AuthControllerAspects;
import io.github.shuoros.ecommercy.config.AspectJConfig;
import io.github.shuoros.ecommercy.config.OpenApiConfig;
import io.github.shuoros.ecommercy.control.AuthController;
import io.github.shuoros.ecommercy.control.ExceptionController;
import io.github.shuoros.ecommercy.dao.graphql.query.ProductQuery;
import io.github.shuoros.ecommercy.dao.repository.*;
import io.github.shuoros.ecommercy.security.SecurityConfig;
import io.github.shuoros.ecommercy.security.UnauthorizedEntryPoint;
import io.github.shuoros.ecommercy.security.filter.ExceptionHandlerFilter;
import io.github.shuoros.ecommercy.security.filter.JwtAuthenticationFilter;
import io.github.shuoros.ecommercy.security.filter.RequestsLoggerFilter;
import io.github.shuoros.ecommercy.security.filter.RestDataSecurityFilter;
import io.github.shuoros.ecommercy.security.jwt.Jwt;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EcommercyApplicationTests extends AbstractContainerBaseTest {

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

    @Autowired
    private ProductQuery productQuery;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private BasketItemRepository basketItemRepository;

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private CustomerRepository userRepository;

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

    @Test
    void productQueryLoads() {
        assertNotNull(productQuery);
    }

    @Test
    void addressRepositoryLoads() {
        assertNotNull(addressRepository);
    }

    @Test
    void adminRepositoryLoads() {
        assertNotNull(adminRepository);
    }

    @Test
    void basketItemRepositoryLoads() {
        assertNotNull(basketItemRepository);
    }

    @Test
    void basketRepositoryLoads() {
        assertNotNull(basketRepository);
    }

    @Test
    void categoryRepositoryLoads() {
        assertNotNull(categoryRepository);
    }

    @Test
    void cityRepositoryLoads() {
        assertNotNull(cityRepository);
    }

    @Test
    void countryRepositoryLoads() {
        assertNotNull(countryRepository);
    }

    @Test
    void commentRepositoryLoads() {
        assertNotNull(commentRepository);
    }

    @Test
    void groupRepositoryLoads() {
        assertNotNull(groupRepository);
    }

    @Test
    void orderRepositoryLoads() {
        assertNotNull(orderRepository);
    }

    @Test
    void orderItemRepositoryLoads() {
        assertNotNull(orderItemRepository);
    }

    @Test
    void productRepositoryLoads() {
        assertNotNull(productRepository);
    }

    @Test
    void stateRepositoryLoads() {
        assertNotNull(stateRepository);
    }

    @Test
    void userRepositoryLoads() {
        assertNotNull(userRepository);
    }

}
