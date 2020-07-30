package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.CategoryService;
import kr.co.fastcampus.eatgo.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    CategoryService categoryService;

    @Test
    public void list() throws Exception {
        List<Category> categories = new ArrayList<>();
        categories.add(Category.builder().name("Korean Food").build());
        given(categoryService.getCategories()).willReturn(categories);

        mvc.perform(get("/categories"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("Korean Food")));

    }

    @Test
    public void create() throws Exception {
        Category category = Category.builder().name("Korean Food").build();
        given(categoryService.addCategory("Korean Food")).willReturn(category);

        mvc.perform(post("/categories")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"name\":\"Korean Food\"}"))
                    .andExpect(status().isCreated())
                    .andExpect(content().string("{}"));

        verify(categoryService, times(1)).addCategory("Korean Food");
    }
}