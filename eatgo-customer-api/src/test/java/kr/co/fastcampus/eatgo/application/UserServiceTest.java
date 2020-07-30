package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.RestaurantNotFoundException;
import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class UserServiceTest {
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository, passwordEncoder);

        User user = User.builder().name("Admin")
                                  .password("test")
                                  .email("mses1340@naver.com").build();

        given(userRepository.save(any())).willReturn(user);
        given(userRepository.findByEmail("mses1340@naver.com")).willReturn(Optional.of(user));
    }

    @Test
    public void registerUserWithNotExist(){
        User user = userService.registerUser("mses123@naver.com", "Admin", "test");
        verify(userRepository, times(1)).save(any());
        assertThat(user.getName()).isEqualTo("Admin");
    }

    @Test
    public void registerUserWithExist(){
        Exception exception = assertThrows(EmailExistedException.class, () -> {
            throw new EmailExistedException("mses1572@naver.com");
        });

        User user = userService.registerUser("mses1572@naver.com", "Admin", "test");
        assertEquals("Email is already registered: mses1572@naver.com", exception.getMessage());
        verify(userRepository, never()).save(user);
    }

}