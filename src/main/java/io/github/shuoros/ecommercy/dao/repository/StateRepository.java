package io.github.shuoros.ecommercy.dao.repository;

import io.github.shuoros.ecommercy.dao.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "state", path = "state")
public interface StateRepository extends JpaRepository<State, String> {
}
