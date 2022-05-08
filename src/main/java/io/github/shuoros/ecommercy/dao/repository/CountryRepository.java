package io.github.shuoros.ecommercy.dao.repository;

import io.github.shuoros.ecommercy.dao.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "country", path = "country")
public interface CountryRepository extends JpaRepository<Country, String> {
}
