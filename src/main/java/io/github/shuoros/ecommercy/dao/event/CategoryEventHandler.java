package io.github.shuoros.ecommercy.dao.event;

import io.github.shuoros.ecommercy.dao.Category;
import io.github.shuoros.ecommercy.exception.PayloadException;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@RepositoryEventHandler(Category.class)
@Component
public class CategoryEventHandler {

    @HandleBeforeCreate
    public void handleBeforeCreate(Category category) {
        if (category.getName() == null || category.getGroup() == null) {
            throw new PayloadException("Missing name or group!", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

}
