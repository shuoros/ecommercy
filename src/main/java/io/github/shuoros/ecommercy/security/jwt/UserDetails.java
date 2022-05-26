package io.github.shuoros.ecommercy.security.jwt;

import io.github.shuoros.ecommercy.dao.Admin;
import io.github.shuoros.ecommercy.dao.User;
import io.github.shuoros.ecommercy.dao.repository.AdminRepository;
import io.github.shuoros.ecommercy.dao.repository.UserRepository;
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

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;

    @Autowired
    public UserDetails(UserRepository userRepository, AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String name)
            throws UsernameNotFoundException {
        if (userRepository.findByEmail(name).isPresent()) {
            User user = userRepository.findByEmail(name).get();
            return getUserDetails(user.getRoles(), user.getEmail(), user.getPassword());
        } else if (adminRepository.findByEmail(name).isPresent()) {
            Admin admin = adminRepository.findByEmail(name).get();
            return getUserDetails(admin.getRoles(), admin.getEmail(), admin.getPassword());
        } else
            throw new UsernameNotFoundException("Invalid email or password.");
    }

    private org.springframework.security.core.userdetails.UserDetails getUserDetails(List<String> roles, String email, String password) {
        List<String> listRoles = new ArrayList<>(roles);
        List<GrantedAuthority> authorities = listRoles.stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(email, password,
                authorities);
    }

}
