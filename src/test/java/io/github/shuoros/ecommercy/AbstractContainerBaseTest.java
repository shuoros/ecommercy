package io.github.shuoros.ecommercy;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@DirtiesContext
public class AbstractContainerBaseTest {

    @Container
    public static MariaDBContainer<?> mariaDb = new MariaDBContainer<>
            ("mariadb:10.4.25")
            .withDatabaseName("ecommercy")
            .withUsername("admin")
            .withPassword("admin");

    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mariaDb::getJdbcUrl);
        registry.add("spring.datasource.username", mariaDb::getUsername);
        registry.add("spring.datasource.password", mariaDb::getPassword);
    }

}
