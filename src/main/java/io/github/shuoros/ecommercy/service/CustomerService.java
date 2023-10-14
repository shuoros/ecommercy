package io.github.shuoros.ecommercy.service;

import io.github.shuoros.ecommercy.exception.EmailAlreadyUsedException;
import io.github.shuoros.ecommercy.exception.UsernameAlreadyUsedException;
import io.github.shuoros.ecommercy.model.domian.Customer;
import io.github.shuoros.ecommercy.model.enumeration.Role;
import io.github.shuoros.ecommercy.model.vm.RegisterVM;
import io.github.shuoros.ecommercy.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Locale;
import java.util.Set;

@Service
@Slf4j
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(final RegisterVM registerVM) {
        checkExistenceOfCustomer(registerVM);

        final Customer newCustomer = Customer.builder()
                .username(registerVM.getUsername().toLowerCase(Locale.ENGLISH))
                .email(StringUtils.hasText(registerVM.getEmail()) ? registerVM.getEmail() : null)
                .password(passwordEncoder.encode(registerVM.getPassword()))
                .activated(true)
                .roles(Set.of(Role.CUSTOMER))
                .build();
        customerRepository.save(newCustomer);
        log.debug("new customer registered: {}", newCustomer);
    }

    private void checkExistenceOfCustomer(final RegisterVM registerVM) {
        customerRepository.findByUsername(registerVM.getUsername().toLowerCase(Locale.ENGLISH))
                .ifPresent(existingCustomer -> {
                    if (existingCustomer.isActivated()) {
                        throw new UsernameAlreadyUsedException();
                    }
                });
        if (StringUtils.hasText(registerVM.getEmail())) {
            customerRepository.findByEmailIgnoreCase(registerVM.getEmail())
                    .ifPresent(existingCustomer -> {
                        if (existingCustomer.isActivated()) {
                            throw new EmailAlreadyUsedException();
                        }
                    });
        }
    }
}
