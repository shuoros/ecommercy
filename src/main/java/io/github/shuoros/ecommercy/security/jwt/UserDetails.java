package io.github.shuoros.ecommercy.security.jwt;

import io.github.shuoros.ecommercy.dao.Admin;
import io.github.shuoros.ecommercy.dao.Customer;
import io.github.shuoros.ecommercy.dao.Role;
import io.github.shuoros.ecommercy.dao.repository.AdminRepository;
import io.github.shuoros.ecommercy.dao.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service(value = "userDetailsService")
public class UserDetails implements UserDetailsService {

    private final CustomerRepository customerRepository;
    private final AdminRepository adminRepository;

    @Autowired
    public UserDetails(CustomerRepository customerRepository, AdminRepository adminRepository) {
        this.customerRepository = customerRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String name)
            throws UsernameNotFoundException {
        if (customerRepository.findByEmail(name).isPresent()) {
            Customer customer = customerRepository.findByEmail(name).get();
            return getUserDetails(List.of(Role.builder().name("CUSTOMER").build()), customer.getEmail(), customer.getPassword());
        } else if (adminRepository.findByEmail(name).isPresent()) {
            Admin admin = adminRepository.findByEmail(name).get();
            return getUserDetails(admin.getRoles(), admin.getEmail(), admin.getPassword());
        } else
            throw new UsernameNotFoundException("Invalid email or password.");
    }

    private org.springframework.security.core.userdetails.UserDetails getUserDetails(List<Role> roles, String email, String password) {
        List<GrantedAuthority> authorities = roles.stream().map(Role::getName).map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(email, password,
                authorities);
    }

}
