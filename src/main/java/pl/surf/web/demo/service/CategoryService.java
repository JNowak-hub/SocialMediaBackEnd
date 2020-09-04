package pl.surf.web.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.surf.web.demo.exceptions.CategoryAlreadyExists;
import pl.surf.web.demo.exceptions.CategoryNotFoundException;
import pl.surf.web.demo.model.Category;
import pl.surf.web.demo.repository.CategoryRepo;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private CategoryRepo categoryRepo;

    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }


    public Category findCategoryById(Long id) {
        Optional<Category> category = categoryRepo.findById(id);
        if (category.isEmpty()) {
            throw new CategoryNotFoundException("Category with id : " + id + " Doesn't exists");
        }
        return category.get();

    }

    public List<Category> getCategories() {
        return categoryRepo.findAll();
    }

    private Boolean checkIfCategoryAlreadyExists(Category category) {
        return categoryRepo.existsByDescription(category.getDescription());
    }

    @Transactional
    public Long addCategory(Category category) {
        if (checkIfCategoryAlreadyExists(category)) {
            throw new CategoryAlreadyExists("Category: " + category.getDescription() + " already exists in database");
        }
        return categoryRepo.save(category).getId();
    }

    @Transactional
    public void deleteCategory(Long categoryId) {
        categoryRepo.deleteCategory(categoryId);
    }
}
