package io.github.shuoros.ecommercy.security;

import io.github.shuoros.ecommercy.security.filter.ExceptionHandlerFilter;
import io.github.shuoros.ecommercy.security.filter.JwtAuthenticationFilter;
import io.github.shuoros.ecommercy.security.filter.RequestsLoggerFilter;
import io.github.shuoros.ecommercy.security.filter.RestDataSecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource(name = "userDetailsService")
    private UserDetailsService userDetailsService;

    private final UnauthorizedEntryPoint unauthorizedEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final ExceptionHandlerFilter exceptionHandlerFilter;
    private final RequestsLoggerFilter requestsLoggerFilter;
    private final RestDataSecurityFilter restDataSecurityFilter;

    @Autowired
    public SecurityConfig(UnauthorizedEntryPoint unauthorizedEntryPoint, JwtAuthenticationFilter jwtAuthenticationFilter,//
                          ExceptionHandlerFilter exceptionHandlerFilter, RequestsLoggerFilter requestsLoggerFilter,
                          RestDataSecurityFilter restDataSecurityFilter) {
        this.unauthorizedEntryPoint = unauthorizedEntryPoint;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.exceptionHandlerFilter = exceptionHandlerFilter;
        this.requestsLoggerFilter = requestsLoggerFilter;
        this.restDataSecurityFilter = restDataSecurityFilter;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http//
                // TODO: Remember to re enable csrf
                // https://spring.io/blog/2013/08/21/spring-security-3-2-0-rc1-highlights-csrf-protection/
                .csrf().disable()//
                .authorizeRequests()//
                // Admin API
                .antMatchers(HttpMethod.POST, "/admin").hasAnyAuthority("ROOT")//
                .antMatchers("/admin/**").hasAnyAuthority("ADMIN")//
                // User API
                .antMatchers(HttpMethod.POST, "/user").permitAll()//
                .antMatchers(HttpMethod.GET, "/user").hasAnyAuthority("ADMIN")//
                .antMatchers("/user/**").hasAnyAuthority("USER", "ADMIN")//
                // Address API
                .antMatchers(HttpMethod.GET, "/address").hasAnyAuthority("ADMIN")//
                .antMatchers(HttpMethod.POST, "/address").hasAnyAuthority("USER", "ADMIN")//
                .antMatchers("/address/**").hasAnyAuthority("USER", "ADMIN")//
                // Basket API
                .antMatchers(HttpMethod.POST, "/basket").denyAll()//
                .antMatchers(HttpMethod.PUT, "/basket/**").denyAll()//
                .antMatchers(HttpMethod.PATCH, "/basket/**").denyAll()//
                .antMatchers(HttpMethod.DELETE, "/basket/**").denyAll()//
                .antMatchers(HttpMethod.GET, "/basket").hasAnyAuthority("ADMIN")//
                .antMatchers(HttpMethod.GET, "/basket/**").hasAnyAuthority("USER", "ADMIN")//
                // BasketItem API
                .antMatchers(HttpMethod.GET, "/basketItem").hasAnyAuthority("ADMIN")//
                .antMatchers(HttpMethod.POST, "/basketItem").hasAnyAuthority("USER", "ADMIN")//
                .antMatchers("/basketItem/**").hasAnyAuthority("USER", "ADMIN")//
                // Comment API
                .antMatchers(HttpMethod.GET, "/comment").hasAnyAuthority("ADMIN")//
                .antMatchers(HttpMethod.POST, "/comment").hasAnyAuthority("USER", "ADMIN")//
                .antMatchers("/comment/**").hasAnyAuthority("USER", "ADMIN")//
                // Product API
                .antMatchers(HttpMethod.POST, "/product").hasAnyAuthority("ADMIN")//
                .antMatchers(HttpMethod.PUT, "/product/**").hasAnyAuthority("ADMIN")//
                .antMatchers(HttpMethod.PATCH, "/product/**").hasAnyAuthority("ADMIN")//
                .antMatchers(HttpMethod.DELETE, "/product/**").hasAnyAuthority("ADMIN")//
                .antMatchers(HttpMethod.GET, "/product").permitAll()//
                .antMatchers(HttpMethod.GET, "/product/**").permitAll()//
                // ProductGroup API
                .antMatchers(HttpMethod.POST, "/group").hasAnyAuthority("ADMIN")//
                .antMatchers(HttpMethod.PUT, "/group/**").hasAnyAuthority("ADMIN")//
                .antMatchers(HttpMethod.PATCH, "/group/**").hasAnyAuthority("ADMIN")//
                .antMatchers(HttpMethod.DELETE, "/group/**").hasAnyAuthority("ADMIN")//
                .antMatchers(HttpMethod.GET, "/group").permitAll()//
                .antMatchers(HttpMethod.GET, "/group/**").permitAll()//
                // ProductCategory API
                .antMatchers(HttpMethod.POST, "/category").hasAnyAuthority("ADMIN")//
                .antMatchers(HttpMethod.PUT, "/category/**").hasAnyAuthority("ADMIN")//
                .antMatchers(HttpMethod.PATCH, "/category/**").hasAnyAuthority("ADMIN")//
                .antMatchers(HttpMethod.DELETE, "/category/**").hasAnyAuthority("ADMIN")//
                .antMatchers(HttpMethod.GET, "/category").permitAll()//
                .antMatchers(HttpMethod.GET, "/category/**").permitAll()//
                // Other Endpoints
                .antMatchers("/graphql").permitAll()//
                .antMatchers("/graphiql").permitAll()//
                .antMatchers("/vendor/graphiql/**").permitAll()//
                .antMatchers("/subscriptions").permitAll()//
                .antMatchers("/swagger-ui").permitAll()//
                .antMatchers("/favicon.ico").permitAll()//
                .anyRequest().authenticated()//
                .and()//
                .exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint)//
                .and()//
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(exceptionHandlerFilter, JwtAuthenticationFilter.class);
        http.addFilterBefore(requestsLoggerFilter, ExceptionHandlerFilter.class);
        http.addFilterAfter(restDataSecurityFilter, JwtAuthenticationFilter.class);
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
