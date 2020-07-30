package kr.co.fastcampus.eatgo.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    @Test
    public void creation(){
        User user = User.builder().email("mses1572@naver.com").name("Hyeon").level(1).build();

        assertThat(user.getEmail()).isEqualTo("mses1572@naver.com");
        assertThat(user.getName()).isEqualTo("Hyeon");
        assertThat(user.isAdmin()).isFalse();
    }

    @Test
    public void isRestaurantOwner(){
        User user = User.builder().level(2).build();

        assertThat(user.isRestaurantOwner()).isTrue();
    }
}