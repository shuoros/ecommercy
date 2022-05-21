package io.github.shuoros.ecommercy.dao.event;

import io.github.shuoros.ecommercy.dao.Basket;
import io.github.shuoros.ecommercy.dao.User;
import io.github.shuoros.ecommercy.dao.repository.BasketRepository;
import io.github.shuoros.ecommercy.dao.service.UserService;
import io.github.shuoros.ecommercy.exception.PayloadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        if (!isValidPassword(user.getPassword()))
            throw new PayloadException("Your password is not compatible! Consider choosing password with " +
                    "1-at least 8 characters and at most 20 characters. " +
                    "2-at least one digit. " +
                    "3-at least one upper case alphabet. " +
                    "4-at least one lower case alphabet. " +
                    "5-at least one special character which includes !@#$%&*()-+=^.", HttpStatus.UNPROCESSABLE_ENTITY);

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    }

    @HandleAfterCreate
    public void handleAfterCreateUser(final User user) {
        basketRepository.save(Basket.builder()//
                .user(user)//
                .build());
    }

    private boolean isValidPassword(final String password) {
        /*
         * It contains at least 8 characters and at most 20 characters.
         * It contains at least one digit.
         * It contains at least one upper case alphabet.
         * It contains at least one lower case alphabet.
         * It contains at least one special character which includes !@#$%&*()-+=^.
         * It doesn't contain any white space.
         */
        final String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        return m.matches();
    }

}
