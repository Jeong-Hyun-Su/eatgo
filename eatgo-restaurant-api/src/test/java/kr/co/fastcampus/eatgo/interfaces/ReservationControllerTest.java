package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.ReservationService;
import kr.co.fastcampus.eatgo.domain.Reservation;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    private ReservationService reservationService;

    @Test
    public void list() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6OSwibmFtZSI6Iuygle2YhOyImCIsInJlc3RhdXJhbnRJZCI6MX0.6cElwOdKPvA1f4wvySa7JQDjm4cvA4IpF3JqD1XmkUE";

        mvc.perform(get("/reservations")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        verify(reservationService).getReservations(1);
    }
}