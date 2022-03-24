package io.github.shuoros.ecommercy.dao.service;

import io.github.shuoros.ecommercy.dao.User;
import io.github.shuoros.ecommercy.dao.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void create(User user) {
        userRepository.save(user);
    }

    public Optional<User> getUser(String email) {
        return userRepository.findByEmail(email);
    }

    public void update(User user) {
        userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public List<User> all() {
        return userRepository.findAll();
    }

}
