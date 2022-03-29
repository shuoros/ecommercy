package io.github.shuoros.ecommercy.dao.service;

import io.github.shuoros.ecommercy.dao.Admin;
import io.github.shuoros.ecommercy.dao.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public void create(Admin admin) {
        adminRepository.save(admin);
    }

    public Optional<Admin> get(String email) {
        return adminRepository.findByEmail(email);
    }

    public void update(Admin admin) {
        adminRepository.save(admin);
    }

    public void delete(Admin admin) {
        adminRepository.delete(admin);
    }

    public List<Admin> all() {
        return adminRepository.findAll();
    }

}
