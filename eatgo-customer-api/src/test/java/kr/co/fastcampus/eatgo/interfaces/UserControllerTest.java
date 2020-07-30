package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.UserService;
import kr.co.fastcampus.eatgo.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    public void create() throws Exception{
        User user = User.builder().name("Admin").password("test")
                                  .email("mses1572@naver.com").build();

        given(userService.registerUser("mses1572@naver.com", "Admin", "test")).willReturn(user);

        mvc.perform(post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"id\":1004,\"email\":\"mses1572@naver.com\",\"name\":\"Admin\",\"password\":\"test\"}"))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("location","/users/1004"));

        verify(userService).registerUser(
                eq("mses1572@naver.com"), eq("Admin"), eq("test"));
    }
}