package io.github.shuoros.ecommercy.dao.service;

import io.github.shuoros.ecommercy.dao.Category;
import io.github.shuoros.ecommercy.dao.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    
    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void create(Category category) {
        categoryRepository.save(category);
    }

    public Optional<Category> get(String name) {
        return categoryRepository.findByName(name);
    }

    public void update(Category category) {
        categoryRepository.save(category);
    }

    public void delete(Category category) {
        categoryRepository.delete(category);
    }

    public List<Category> all() {
        return categoryRepository.findAll();
    }
    
}
