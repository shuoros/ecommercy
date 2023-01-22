package io.github.shuoros.ecommercy.dao.repository;

import io.github.shuoros.ecommercy.dao.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "basket", path = "basket")
public interface BasketRepository extends JpaRepository<Basket, String> {
}
