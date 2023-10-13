package io.github.shuoros.ecommercy.security;

import io.github.shuoros.ecommercy.model.domian.Customer;
import io.github.shuoros.ecommercy.model.enumeration.Role;
import io.github.shuoros.ecommercy.repository.CustomerRepository;
import io.github.shuoros.ecommercy.utils.EmailValidator;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Component("userDetailsService")
public class AuthenticableUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (EmailValidator.validate(username)) {
            return getUserDetails(customerRepository.findOneByEmailIgnoreCase(username), username);
        }
        return getUserDetails(customerRepository.findOneByUsername(username.toLowerCase(Locale.ENGLISH)), username);
    }

    private UserDetails getUserDetails(Optional<Customer> optionalCustomer, String username) {
        return optionalCustomer.map(user -> buildUserDetails(user, username))
                .orElseThrow(() ->
                        new UsernameNotFoundException("Customer " + username + " was not found in the database")
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
