package io.github.shuoros.ecommercy.security;

import io.github.shuoros.ecommercy.security.filter.ExceptionHandlerFilter;
import io.github.shuoros.ecommercy.security.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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

    @Autowired
    public SecurityConfig(UnauthorizedEntryPoint unauthorizedEntryPoint, JwtAuthenticationFilter jwtAuthenticationFilter,//
                          ExceptionHandlerFilter exceptionHandlerFilter) {
        this.unauthorizedEntryPoint = unauthorizedEntryPoint;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.exceptionHandlerFilter = exceptionHandlerFilter;
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
                .antMatchers("/favicon.ico", "/error", "/authenticate", "/signup", "/api/**").permitAll()//
                .antMatchers("/admin/**").hasAnyAuthority("ADMIN")//
                .antMatchers("/user/**").hasAnyAuthority("USER")//
                .anyRequest().authenticated()//
                .and()//
                .exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint)//
                .and()//
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(exceptionHandlerFilter, JwtAuthenticationFilter.class);
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
