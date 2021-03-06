package io.github.shuoros.ecommercy.dao.repository;

import io.github.shuoros.ecommercy.dao.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "group", path = "group")
public interface GroupRepository extends JpaRepository<Group, String> {
}
