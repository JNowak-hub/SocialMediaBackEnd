package pl.surf.web.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.surf.web.demo.model.Category;
import pl.surf.web.demo.repository.CategoryRepo;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CategoryControllerTest {


    private MockMvc mockMvc;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private WebApplicationContext context;

    ObjectMapper om = new ObjectMapper();

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @WithMockUser("ADMIN")
    public void addCategoryTest() throws Exception {
        Category category = new Category("TestCategory");
        String jsonCategory = om.writeValueAsString(category);
        MvcResult result = mockMvc.perform(post("/api/category")
                .content(jsonCategory)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Long resultContent = Long.parseLong(result.getResponse().getContentAsString());
        assertThat(resultContent).isEqualTo(4L);
    }


    @Test
    @WithMockUser("ADMIN")
    public void shouldFindCategoryByIdTest() throws Exception {
        mockMvc.perform(get("/api/category/1")

                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", is("KITE SURF")));
    }

    @Test
    @WithMockUser("ADMIN")
    public void shouldReturnListOfCategoriesTest() throws Exception {
        mockMvc.perform(get("/api/category")

                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].description", is("KITE SURF")));
    }


    @Test
    @WithMockUser("ADMIN")
    public void shouldDeleteCategoryTest() throws Exception {
        mockMvc.perform(delete("/api/category?categoryId=2"))

                .andExpect(status().isOk());
        final Optional<Category> categoryOptional = categoryRepo.findById(2L);
        assertThat(false).isEqualTo(categoryOptional.isPresent());
    }
}
