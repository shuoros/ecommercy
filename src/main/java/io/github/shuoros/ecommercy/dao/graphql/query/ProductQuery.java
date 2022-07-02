package io.github.shuoros.ecommercy.dao.graphql.query;

import graphql.kickstart.tools.GraphQLQueryResolver;
import io.github.shuoros.ecommercy.dao.Product;
import io.github.shuoros.ecommercy.dao.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductQuery implements GraphQLQueryResolver {

    private final ProductRepository productRepository;

    @Autowired
    public ProductQuery(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public long countProducts() {
        return productRepository.count();
    }

}
