package io.github.shuoros.ecommercy.repository;

import io.github.shuoros.ecommercy.model.domian.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    Optional<Customer> findByEmailIgnoreCase(String email);

    Optional<Customer> findByUsername(String username);
}
