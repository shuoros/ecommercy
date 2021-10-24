package io.github.shuoros.allAboutSpring.model.dbs;

import io.github.shuoros.allAboutSpring.model.schema.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * simple CRUD repo for {@link User} entity, wrapper around your SQL db
 */
@Repository
public interface UserRepositorySQL extends CrudRepository<User,String> {}
