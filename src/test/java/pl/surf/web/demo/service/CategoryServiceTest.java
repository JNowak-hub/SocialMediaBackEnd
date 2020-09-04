package pl.surf.web.demo.service;


import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import pl.surf.web.demo.exceptions.CategoryAlreadyExists;
import pl.surf.web.demo.exceptions.CategoryNotFoundException;
import pl.surf.web.demo.model.Category;
import pl.surf.web.demo.repository.CategoryRepo;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;


@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
class CategoryServiceTest {

    @Autowired
    CategoryService categoryService;
    @Autowired
    CategoryRepo categoryRepo;


    Category category1 = new Category("category1");
    Category category3 = new Category("KITE SURF");
    Category category2 = new Category("KITE xxx");


    @Test
    @Order(1)
    public void shouldFindById() {
        assertThat(categoryService.findCategoryById(2L)).isEqualTo(category1);
    }


    @Test
    @Order(2)
    public void shouldFindByIdButThrowsException() {
        Exception exception = assertThrows(CategoryNotFoundException.class, () -> {
            categoryService.findCategoryById(20L);
        });

        assertThat(exception).isInstanceOf(CategoryNotFoundException.class);
    }

    @Test
    @Order(3)
    public void shouldAddCategory() {
        assertThat(categoryService.addCategory(category2)).isEqualTo(4);
    }

    @Test
    @Order(4)
    public void shouldAddCategoryButThrowsException() {
        Exception exception = assertThrows(CategoryAlreadyExists.class, () -> {
            categoryService.addCategory(category1);
        });

        assertThat(exception).isInstanceOf(CategoryAlreadyExists.class);
    }


    @Test
    @Order(5)
    void shouldGetCategories() {
        categoryRepo.save(category1);
        final List<Category> categories = categoryService.getCategories();

        assertThat(categories.get(0).getDescription()).isEqualTo(category3.getDescription());
    }

    @Test
    @Order(6)
    void shouldDeleteCategory() {
        categoryService.deleteCategory(2L);
        Exception exception = assertThrows(CategoryNotFoundException.class, () -> {
            categoryService.findCategoryById(2L);
        });

        assertThat(exception).isInstanceOf(CategoryNotFoundException.class);
    }
}
