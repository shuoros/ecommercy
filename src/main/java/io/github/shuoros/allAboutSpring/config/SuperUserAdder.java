package io.github.shuoros.allAboutSpring.config;

import io.github.shuoros.allAboutSpring.model.dbs.UserRepositorySQL;
import io.github.shuoros.allAboutSpring.model.schema.User;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SuperUserAdder implements CommandLineRunner {

    @Autowired
    UserRepositorySQL repository;

    @Value("${admin.username}") String username;
    @Value("${admin.password}") String password;

    @Override
    public void run(String... args) throws Exception {
        var sUser = new User(username,password);
        repository.save(sUser);
        LoggerFactory.getLogger(SuperUserAdder.class).info("added {} as super user",sUser);
    }
}
