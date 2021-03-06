package io.github.shuoros.ecommercy.dao.repository;

import io.github.shuoros.ecommercy.dao.Comment;
import io.github.shuoros.ecommercy.dao.Product;
import io.github.shuoros.ecommercy.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "comment", path = "comment")
public interface CommentRepository extends JpaRepository<Comment, String> {

    boolean existsByUserAndProduct(User user, Product product);

}
