package io.github.shuoros.ecommercy.dao.event;

import io.github.shuoros.ecommercy.dao.Comment;
import io.github.shuoros.ecommercy.dao.repository.CommentRepository;
import io.github.shuoros.ecommercy.dao.repository.ProductRepository;
import io.github.shuoros.ecommercy.exception.PayloadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@RepositoryEventHandler(Comment.class)
@Component
public class CommentEventHandler {

    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CommentEventHandler(CommentRepository commentRepository, ProductRepository productRepository) {
        this.commentRepository = commentRepository;
        this.productRepository = productRepository;
    }

    @HandleBeforeCreate
    public void handleBeforeCreate(final Comment comment) {
        if (comment.getStar() == null || comment.getProduct() == null || comment.getUser() == null)
            throw new PayloadException("Missing star, product or user!", HttpStatus.UNPROCESSABLE_ENTITY);
        if (commentRepository.existsByUserAndProduct(comment.getUser(), comment.getProduct()))
            throw new PayloadException("User already commented this product!", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @HandleAfterCreate
    public void handleAfterCreate(final Comment comment) {
        calculateStars(comment);
    }

    @HandleAfterSave
    public void handleAfterSave(final Comment comment) {
        calculateStars(comment);
    }

    @HandleAfterDelete
    public void handleAfterDelete(final Comment comment) {
        calculateStars(comment);
    }

    private void calculateStars(Comment comment) {
        List<Comment> comments = comment.getProduct().getComments();
        float average = 0;
        for (Comment c : comments) {
            average += c.getStar();
        }
        average /= comments.size();
        comment.getProduct().setStars(average);
        productRepository.save(comment.getProduct());
    }

}
