package kr.co.fastcampus.eatgo.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {
    @Test
    public void category(){
        Category category = Category.builder().name("Korean food").build();

        assertThat(category.getName()).isEqualTo("Korean food");
    }
}