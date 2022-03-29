package io.github.shuoros.ecommercy.control;

import io.github.shuoros.ecommercy.dao.Admin;
import io.github.shuoros.ecommercy.dao.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @EventListener
    @Async
    public void initAdmin(ApplicationReadyEvent e) {
        if (adminService.all().size() == 0) {
            String[] roles = {"ADMIN", "ROOT"};
            adminService.create(Admin.builder()//
                    .name("root")//
                    .email("admin@ecommercy.app")//
                    .password(new BCryptPasswordEncoder().encode("1234"))//
                    .roles(Arrays.asList(roles))//
                    .build());
            log.info("Admin initialized ...");
        } else
            log.info("Admin already initialized ...");
    }

}
