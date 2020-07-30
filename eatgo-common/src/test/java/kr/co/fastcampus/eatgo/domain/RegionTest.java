package kr.co.fastcampus.eatgo.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RegionTest {
    @Test
    public void create(){
        Region region = Region.builder().name("서울").build();

        assertThat(region.getName()).isEqualTo("서울");
    }
}