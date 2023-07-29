package io.github.shuoros.ecommercy.repository;

import io.github.shuoros.ecommercy.domian.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
}
