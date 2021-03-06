package io.github.shuoros.ecommercy.dao.repository;

import io.github.shuoros.ecommercy.dao.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "orderItem", path = "orderItem")
public interface OrderItemRepository extends JpaRepository<OrderItem, String> {
}
