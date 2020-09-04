package pl.surf.web.demo.controller;

import org.springframework.data.repository.cdi.Eager;
import org.springframework.web.bind.annotation.*;
import pl.surf.web.demo.model.Category;
import pl.surf.web.demo.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("api/category")
@CrossOrigin(origins = "{cross.origin}")

public class CategoryController {

    private CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }


    @GetMapping("/{categoryId}")
    public Category findCategoryById(@PathVariable Long categoryId) {
        return service.findCategoryById(categoryId);
    }

    @GetMapping
    public List<Category> getCategories() {
        return service.getCategories();
    }

    @PostMapping
    public Long addCategory(@RequestBody Category category) {
        return service.addCategory(category);
    }

    @DeleteMapping("{categoryId}")
    public void deleteCategory(@PathVariable Long categoryId) {
        service.deleteCategory(categoryId);
    }
}
