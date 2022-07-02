package io.github.shuoros.ecommercy.dao.repository;

import io.github.shuoros.ecommercy.dao.Basket;
import io.github.shuoros.ecommercy.dao.BasketItem;
import io.github.shuoros.ecommercy.dao.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "basketItem", path = "basketItem")
public interface BasketItemRepository extends JpaRepository<BasketItem, String> {

    boolean existsByBasketAndProduct(Basket basket, Product product);

}
