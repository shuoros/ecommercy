package io.github.shuoros.ecommercy.dao.graphql.query;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTest;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductQueryTests {

    private static final String QUERY_REQUEST_PATH = "graphql/sample/requests/%s.graphql";
    private static final String QUERY_RESPONSE_PATH = "graphql/sample/responses/%s.json";

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @Test
    void getAllProductsMustReturnAllProducts() throws IOException {
        // given
        var testName = "getAllProducts";

        // when
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(//
                String.format(QUERY_REQUEST_PATH, testName));

        // then
        var expectedResponseBody = read(String.format(QUERY_RESPONSE_PATH, testName));
        assertEquals(HttpStatus.OK, graphQLResponse.getStatusCode());
        assertEquals(expectedResponseBody, graphQLResponse.getRawResponse().getBody());
    }

    private String read(String location) throws IOException {
        return new String(new ClassPathResource(location).getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }

}
