package io.github.shuoros.ecommercy.dao.event;

import io.github.shuoros.ecommercy.dao.Basket;
import io.github.shuoros.ecommercy.dao.User;
import io.github.shuoros.ecommercy.dao.repository.BasketRepository;
import io.github.shuoros.ecommercy.dao.service.UserService;
import io.github.shuoros.ecommercy.exception.PayloadException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@RepositoryEventHandler(User.class)
@Component
public class UserEventHandler {

    private final UserService userService;
    private final BasketRepository basketRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserEventHandler(UserService userService, BasketRepository basketRepository,//
                            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.basketRepository = basketRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @HandleBeforeCreate
    public void handleBeforeCreate(final User user) {
        if (user.getName() == null || user.getEmail() == null || user.getPassword() == null)
            throw new PayloadException("Missing name, email or password!", HttpStatus.UNPROCESSABLE_ENTITY);
        if (userService.get(user.getEmail()).isPresent())
            throw new PayloadException("User with such email already exists!", HttpStatus.CONFLICT);

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    }

    @HandleAfterCreate
    public void handleAfterCreateUser(final User user) {
        basketRepository.save(Basket.builder()//
                .user(user)//
                .build());
    }

}
