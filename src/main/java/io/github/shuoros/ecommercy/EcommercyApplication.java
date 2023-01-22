package io.github.shuoros.ecommercy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources({
        @PropertySource("classpath:application.properties"),
        @PropertySource("classpath:application-graphql.properties"),
        @PropertySource("classpath:application-jwt.properties"),
        @PropertySource("classpath:application-mysql.properties"),
        @PropertySource("classpath:application-openapi.properties")
})
public class EcommercyApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommercyApplication.class, args);
    }

}
