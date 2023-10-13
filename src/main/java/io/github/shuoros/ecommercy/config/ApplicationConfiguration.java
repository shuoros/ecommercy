package io.github.shuoros.ecommercy.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "ecommercy", ignoreUnknownFields = false)
public class ApplicationConfiguration {

    private Security security;

    @Data
    public static class Security {

        private String secretKey;
        private Long simpleTokenValidity;
        private Long rememberMeTokenValidity;
    }
}
