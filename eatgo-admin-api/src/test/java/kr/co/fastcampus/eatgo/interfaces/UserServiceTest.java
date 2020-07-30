package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.UserService;
import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class UserServiceTest {
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository);

        List<User> users = new ArrayList<>();
        users.add(User.builder()
                    .name("정현수")
                    .email("mses1572@naver.com")
                    .level(3).build());
        users.add(User.builder()
                    .id(1004)
                    .name("Jeong")
                    .email("Admin@example.com")
                    .level(3).build());

        given(userRepository.findAll()).willReturn(users);
        given(userRepository.save(any())).willReturn(users.get(0));
        given(userRepository.findById(any())).willReturn(Optional.of(users.get(1)));
    }

    @Test
    public void getUsers(){
        List<User> users = userService.getUsers();

        User user = users.get(0);
        assertThat(user.getEmail()).isEqualTo("mses1572@naver.com");
        assertThat(user.getName()).isEqualTo("정현수");

        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void addUser(){
        User user = userService.addUser("mses1572@naver.com", "정현수");
        assertThat(user.getName()).isEqualTo("정현수");
    }

    @Test
    public void updateUser(){
        userService.updateUser(1004,"Admin@example.com",  "Jeong", 3);

        verify(userRepository).findById(1004);
    }

    @Test
    public void deactiveUser(){
        User user = userRepository.findAll().get(1);

        assertThat(user.getName()).isEqualTo("Jeong");
        assertThat(user.isActive()).isTrue();

        user.deactive();

        assertThat(user.isActive()).isFalse();
    }
}