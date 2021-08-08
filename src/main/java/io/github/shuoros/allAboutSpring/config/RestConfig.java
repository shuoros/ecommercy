package io.github.shuoros.allAboutSpring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import io.github.shuoros.allAboutSpring.filter.CORSFilter;

/**
 * Contains CORS policy configurations.
 * 
 * @author Soroush Mehrad
 * @version 1.0.0
 * @since 2021-08-08
 */
@Configuration
public class RestConfig {

	/**
	 * Set CORS policy for allowed origin and methods in application.
	 * @return new CORS filter configurations.
	 * @since v1.0.0
	 */
	@Bean
	public CORSFilter corsFilter() {
		CorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOrigin("*");
		config.addAllowedMethod(HttpMethod.DELETE);
		config.addAllowedMethod(HttpMethod.GET);
		config.addAllowedMethod(HttpMethod.OPTIONS);
		config.addAllowedMethod(HttpMethod.PUT);
		config.addAllowedMethod(HttpMethod.POST);
		((UrlBasedCorsConfigurationSource) source).registerCorsConfiguration("/**", config);
		return new CORSFilter(source);
	}
}