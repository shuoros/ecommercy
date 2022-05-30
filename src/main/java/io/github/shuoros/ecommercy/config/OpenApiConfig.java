package io.github.shuoros.ecommercy.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Ecommercy API")
                        .description("The Swagger documentation for the Ecommercy APIs")
                        .summary("A REST-API ecommerce application with Spring Boot and MySQL." +
                                " I used various technologies to build this application such as Spring Security and JWT" +
                                " authentication and authorization, Hibernate, Spring Data Rest and GraphQL. I also used" +
                                " Swagger to generate the documentation for the API.")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Soroush")
                                .url("https://github.com/shuoros")
                                .email("shuoros@yahoo.com"))
                        .termsOfService("https://github.com/shuoros/ecommercy/blob/main/README.md")
                        .license(new License().name("MIT license").url("https://opensource.org/licenses/MIT")));
    }

}
