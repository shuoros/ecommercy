package io.github.shuoros.ecommercy.controller;

import io.github.shuoros.ecommercy.domian.Product;
import io.github.shuoros.ecommercy.repository.ProductRepository;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/products")
    private List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
