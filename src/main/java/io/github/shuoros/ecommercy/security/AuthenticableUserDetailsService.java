package io.github.shuoros.ecommercy.security;

import io.github.shuoros.ecommercy.model.domian.Customer;
import io.github.shuoros.ecommercy.model.enumeration.Role;
import io.github.shuoros.ecommercy.repository.CustomerRepository;
import io.github.shuoros.ecommercy.utils.EmailValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component("userDetailsService")
@Slf4j
public class AuthenticableUserDetailsService implements UserDetailsService {

    private final static String USER_NOT_FOUND_EXCEPTION = "Customer %S was not found in the database";

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Authenticating {}", username);

        if (EmailValidator.validate(username)) {
            return loadUserDetailsByEmail(username);
        }
        return loadUserDetailsByUsername(username);
    }

    private UserDetails loadUserDetailsByEmail(String username) {
        return customerRepository.findOneByEmailIgnoreCase(username)
                .map(user -> buildUserDetails(user, username))
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format(USER_NOT_FOUND_EXCEPTION, username))
                );
    }

    private UserDetails loadUserDetailsByUsername(String username) {
        return customerRepository.findOneByUsername(username.toLowerCase(Locale.ENGLISH))
                .map(user -> buildUserDetails(user, username))
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format(USER_NOT_FOUND_EXCEPTION, username))
                );
    }

    private UserDetails buildUserDetails(final Customer customer, final String username) {
        final List<GrantedAuthority> authorities = customer
                .getRoles()
                .stream()
                .map(Role::toString)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new User(username, customer.getPassword(), authorities);
    }
}
