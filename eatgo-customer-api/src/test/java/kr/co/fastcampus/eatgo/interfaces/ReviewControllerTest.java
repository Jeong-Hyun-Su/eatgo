package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.ReviewService;
import kr.co.fastcampus.eatgo.domain.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private ReviewService reviewService;

    @Test
    public void createWithValidAttributes() throws Exception{
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MTEsIm5hbWUiOiJUZXN0ZXIifQ.Zg_V-n_Aodcx6NG_eQOgdRUDWS6bwa_tPI_qH4vvFps";


        given(reviewService.addReview(5, "Tester", 3, "Mat-It-Da")).willReturn(
                Review.builder().id(11).build()
        );

        // Token을 통해 ID와 Name을 넘겨줌 (.header("Authorization", "Bearer " + token)
        mvc.perform(post("/restaurants/5/reviews")
                        .header("Authorization","Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"score\":3,\"description\":\"Mat-It-Da\"}"))
                        .andExpect(status().isCreated())
                        .andExpect(header().string("location", "/restaurants/5/reviews/11"));

        verify(reviewService).addReview(5, "Tester", 3, "Mat-It-Da");
    }

    @Test
    public void createWithInValidAttributes() throws Exception{
        mvc.perform(post("/restaurants/1/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());

        verify(reviewService, never()).addReview(1, "Tester", 3, "Mat-It-da");
    }
}