package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.Category;
import kr.co.fastcampus.eatgo.domain.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class CategoryServiceTest {
    private CategoryService categoryService;
    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        categoryService = new CategoryService(categoryRepository);
    }
    @Test
    public void getCategories(){
        //given
        List<Category> mockCategories = new ArrayList<>();
        mockCategories.add(Category.builder().name("Korean Food").build());
        mockCategories.add(Category.builder().name("Cyber Food").build());

        given(categoryRepository.findAll()).willReturn(mockCategories);
        //when
        List<Category> categories = categoryService.getCategories();
        Category category = categories.get(0);
        //then
        assertThat(category.getName()).isEqualTo("Korean Food");
        verify(categoryRepository, times(1)).findAll();
    }
    @Test
    public void addCategory(){
        given(categoryRepository.save(any())).will(invocation -> {
           Category category = invocation.getArgument(0);
           return category;
        });

        Category category = categoryService.addCategory("Cyber Food");
        verify(categoryRepository, times(1)).save(any());

        assertThat(category.getName()).isEqualTo("Cyber Food");
    }
}