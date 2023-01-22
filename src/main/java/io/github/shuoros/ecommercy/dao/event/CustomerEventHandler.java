package io.github.shuoros.ecommercy.dao.event;

import io.github.shuoros.ecommercy.dao.Basket;
import io.github.shuoros.ecommercy.dao.Customer;
import io.github.shuoros.ecommercy.dao.repository.BasketRepository;
import io.github.shuoros.ecommercy.dao.repository.CustomerRepository;
import io.github.shuoros.ecommercy.exception.PayloadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RepositoryEventHandler(Customer.class)
@Component
public class CustomerEventHandler {

    private final CustomerRepository customerRepository;
    private final BasketRepository basketRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public CustomerEventHandler(CustomerRepository customerRepository, BasketRepository basketRepository,//
                                BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.customerRepository = customerRepository;
        this.basketRepository = basketRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @HandleBeforeCreate
    public void handleBeforeCreate(final Customer customer) {
        if (customer.getName() == null || customer.getEmail() == null || customer.getPassword() == null)
            throw new PayloadException("Missing name, email or password!", HttpStatus.UNPROCESSABLE_ENTITY);
        if (customerRepository.findByEmail(customer.getEmail()).isPresent())
            throw new PayloadException("User with such email already exists!", HttpStatus.CONFLICT);
        if (customer.getPhoneNumber() != null && customerRepository.findByPhoneNumber(customer.getPhoneNumber()).isPresent())
            throw new PayloadException("User with such phone number already exists!", HttpStatus.CONFLICT);
        if (!isValidPassword(customer.getPassword()))
            throw new PayloadException("Your password is not compatible! Consider choosing password with " +
                    "1-at least 8 characters and at most 20 characters. " +
                    "2-at least one digit. " +
                    "3-at least one upper case alphabet. " +
                    "4-at least one lower case alphabet. " +
                    "5-at least one special character which includes !@#$%&*()-+=^.", HttpStatus.UNPROCESSABLE_ENTITY);

        customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
    }

    @HandleAfterCreate
    public void handleAfterCreateUser(final Customer customer) {
        basketRepository.save(Basket.builder()//
                .customer(customer)//
                .build());
    }

    @HandleBeforeSave
    public void handleBeforeSave(final Customer customer) {
        if (customerRepository.findByEmail(customer.getEmail()).isPresent()//
                && !customerRepository.findByEmail(customer.getEmail()).get().getId().equals(customer.getId()))
            throw new PayloadException("User with such email already exists!", HttpStatus.CONFLICT);
        if (customer.getPhoneNumber() != null//
                && customerRepository.findByPhoneNumber(customer.getPhoneNumber()).isPresent()//
                && !customerRepository.findByPhoneNumber(customer.getPhoneNumber()).get().getId().equals(customer.getId()))
            throw new PayloadException("User with such phone number already exists!", HttpStatus.CONFLICT);
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
