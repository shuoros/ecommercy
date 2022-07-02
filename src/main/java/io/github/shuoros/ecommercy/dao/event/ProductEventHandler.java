package io.github.shuoros.ecommercy.dao.event;

import io.github.shuoros.ecommercy.dao.Product;
import io.github.shuoros.ecommercy.exception.PayloadException;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@RepositoryEventHandler(Product.class)
@Component
public class ProductEventHandler {

    @HandleBeforeCreate
    public void handleBeforeCreate(final Product product) {
        if (product.getGroup() == null || product.getCategories() == null || product.getName() == null ||//
                product.getPrice() == null || product.getInventory() == null) {
            throw new PayloadException("Missing group, categories, name, price or inventory!", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

}
