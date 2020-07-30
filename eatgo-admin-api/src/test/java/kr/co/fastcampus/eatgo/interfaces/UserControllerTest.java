package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.UserService;
import kr.co.fastcampus.eatgo.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    public void list() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(User.builder()
                    .name("Jeong-Hyun-Su")
                    .email("mses1572@naver.com")
                    .level(3).build());

        given(userService.getUsers()).willReturn(users);

        mvc.perform(get("/users"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("Jeong-Hyun-Su")));
    }

    @Test
    public void create() throws Exception {
        mvc.perform(post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"name\":\"admin\", \"email\":\"admin@example.com\", \"level\":3}"))
                    .andExpect(status().isCreated())
                    .andExpect(content().string("{}"));

        String email = "admin@example.com";
        String name = "admin";
        verify(userService, times(1)).addUser(email, name);
    }

    @Test
    public void update() throws Exception {
        mvc.perform(patch("/users/1004")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1004, \"name\":\"admin\", \"email\":\"admin@example.com\", \"level\":3}"))
                .andExpect(status().isOk())
                .andExpect(content().string("{}"));

        String name = "admin";
        String email = "admin@example.com";
        Integer level = 3;
        Integer id = 1004;

        verify(userService, times(1)).updateUser(id, email, name, level);
    }

    @Test
    public void Delete() throws Exception {
        mvc.perform(delete("/users/1004"))
                    .andExpect(status().isOk());

        verify(userService).deactiveUser(1004);
    }
}