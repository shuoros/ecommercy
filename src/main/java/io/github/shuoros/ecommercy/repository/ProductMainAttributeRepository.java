package io.github.shuoros.ecommercy.repository;

import io.github.shuoros.ecommercy.domain.ProductMainAttribute;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProductMainAttribute entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductMainAttributeRepository extends JpaRepository<ProductMainAttribute, Long> {}
