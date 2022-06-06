package io.github.shuoros.ecommercy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources({
        @PropertySource("classpath:application.properties"),
        @PropertySource("classpath:application-mysql.properties")
})
public class EcommercyApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommercyApplication.class, args);
    }

}
