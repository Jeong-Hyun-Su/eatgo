package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
    public void authenticateWithValidAttributes() {
        given(passwordEncoder.matches(any(), any())).willReturn(true);
        User user = userService.authenticate("mses1340@naver.com", "test");

        assertThat(user.getEmail()).isEqualTo("mses1340@naver.com");
    }

    @Test
    public void authenticateWithNotExisted() {
        given(userRepository.findByEmail("tester@example.com")).willReturn(Optional.empty());
        given(passwordEncoder.matches(any(), any())).willReturn(false);

        Throwable exception = assertThrows(EmailNotExistedException.class, () -> {
            userService.authenticate("tester@example.com", "test");
        });
        assertEquals(exception.getMessage(), "Email is not registered: tester@example.com");
    }
}