package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.EmailNotExistedException;
import kr.co.fastcampus.eatgo.application.PasswordWrongException;
import kr.co.fastcampus.eatgo.application.UserService;
import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SessionController.class)
class SessionControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private UserService userService;

    @Test
    public void createWithValidAttributes() throws Exception{
        User user = User.builder().id(1)
                .name("Tester")
                .email("tester@example.com")
                .level(1)
                .password("test").build();

        given(userService.authenticate("tester@example.com", "test"))
                                        .willReturn(user);

        given(jwtUtil.createToken(1, "Tester", null)).willReturn("header.payload.signature");

        mvc.perform(post("/session")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"email\":\"tester@example.com\", \"password\":\"test\"}"))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("location", "/session/1"))
                    .andExpect(content().string(containsString(".")))
                    .andExpect(content().string(containsString("{\"accessToken\":\"header.payload.signature\"}")));

        verify(userService).authenticate("tester@example.com", "test");
    }

    @Test
    public void createRestaurantOwner() throws Exception{
        User user = User.builder().id(1)
                .name("Tester")
                .email("tester@example.com")
                .level(2)
                .restaurantId(369)
                .password("test").build();

        given(userService.authenticate("tester@example.com", "test"))
                .willReturn(user);

        given(jwtUtil.createToken(1, "Tester", 369)).willReturn("header.payload.signature");

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"tester@example.com\", \"password\":\"test\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/session/1"))
                .andExpect(content().string(containsString(".")))
                .andExpect(content().string(containsString("{\"accessToken\":\"header.payload.signature\"}")));

        verify(userService).authenticate("tester@example.com", "test");
    }

    @Test
    public void createWithWrongPassword() throws Exception{
        given(userService.authenticate("tester@example.com", "x"))
                                                .willThrow(PasswordWrongException.class);

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"tester@example.com\", \"password\":\"x\"}"))
                .andExpect(status().isBadRequest());

        verify(userService).authenticate("tester@example.com", "x");
    }

    @Test
    public void createWithNotExistedEmail() throws Exception{
        given(userService.authenticate("x@example.com", "test"))
                .willThrow(EmailNotExistedException.class);

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"x@example.com\", \"password\":\"test\"}"))
                .andExpect(status().isBadRequest());

        verify(userService).authenticate("x@example.com", "test");
    }
}