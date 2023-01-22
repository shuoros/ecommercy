package io.github.shuoros.ecommercy.dao.event;

import io.github.shuoros.ecommercy.dao.Group;
import io.github.shuoros.ecommercy.exception.PayloadException;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@RepositoryEventHandler(Group.class)
@Component
public class GroupEventHandler {

    @HandleBeforeCreate
    public void handleBeforeCreate(final Group group) {
        if(group.getName() == null) {
            throw new PayloadException("Missing name!", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

}
