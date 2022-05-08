package io.github.shuoros.ecommercy.dao.graphql.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import io.github.shuoros.ecommercy.dao.Product;
import io.github.shuoros.ecommercy.dao.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMutation implements GraphQLMutationResolver {

    private final ProductRepository productRepository;

    @Autowired
    public ProductMutation(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(String name, double price, int inventory) {
        return productRepository.save(Product.builder()//
                .name(name)//
                .price(price)//
                .inventory(inventory)//
                .build());
    }

}
