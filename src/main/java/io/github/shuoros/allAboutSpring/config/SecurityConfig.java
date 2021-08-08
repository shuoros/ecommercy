package io.github.shuoros.allAboutSpring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.github.shuoros.allAboutSpring.filter.JwtRequestFilter;
import io.github.shuoros.allAboutSpring.util.UserDetailService;

/**
 * Settings for security and authentication of requests to the server by Json
 * Web Token(JWT).
 * 
 * @author Soroush Mehrad
 * @version 1.0.0
 * @since 2021-08-08
 */
@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * {@link org.springframework.security.core.userdetails.UserDetailsService}
	 * @since v1.0.0
	 */
	@Autowired
	private UserDetailService userDetailService;
	/**
	 * {@link io.github.shuoros.allAboutSpring.filter.JwtRequestFilter}
	 * @since v1.0.0
	 */
	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	/**
	 * Gives the implemented <code>UserDetailService</code> to its interface in
	 * <code>AuthenticationManagerBuilder</code>.
	 * 
	 * @since v1.0.0
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService);
	}

	/**
	 * Enables the need for authentication for all requests that hits the server and
	 * puts <code>JwtRequestFilter</code> for authentication before the controllers.
	 * 
	 * @since v1.0.0
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().exceptionHandling().and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}

	/**
	 * It ignores the given addresses for authentication and they hits the
	 * controllers without authentication.
	 * 
	 * @since v1.0.0
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/", "/api/signup", "/authenticate", "/connect/**", "/lib/**");
	}

	/**
	 * @return A <code>NoOpPasswordEncoder</code> bean.
	 * @since v1.0.0
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	/**
	 * @return A <code>authenticationManagerBean</code> bean from super class.
	 * @since v1.0.0
	 */
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
