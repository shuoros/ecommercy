package io.github.shuoros.ecommercy.security.jwt;

import io.github.shuoros.ecommercy.dao.User;
import io.github.shuoros.ecommercy.dao.service.UserService;
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

    private final UserService userService;

    @Autowired
    public UserDetails(UserService userService) {
        this.userService = userService;
    }

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String name)
            throws UsernameNotFoundException {
        User user;
        if (userService.getUser(name).isPresent())
            user = userService.getUser(name).get();
        else
            throw new UsernameNotFoundException("Invalid email or password.");

        List<String> listRoles = new ArrayList<>(user.getRoles());
        List<GrantedAuthority> authorities = listRoles.stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                authorities);
    }
}
