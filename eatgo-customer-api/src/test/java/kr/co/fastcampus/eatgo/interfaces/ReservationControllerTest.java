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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    private ReservationService reservationService;

    @Test
    public void create() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6OSwibmFtZSI6Iuygle2YhOyImCIsInJlc3RhdXJhbnRJZCI6MX0.6cElwOdKPvA1f4wvySa7JQDjm4cvA4IpF3JqD1XmkUE";

        Integer restaurantId = 3;
        Integer userId = 9;
        String name = "정현수";
        String date = "2019-12-24";
        String time = "20:00";
        Integer partySize = 20;

        given(reservationService.addReservation(restaurantId, userId, name, date, time, partySize))
                                                    .willReturn(Reservation.builder().restaurantId(restaurantId)
                                                                                    .userId(userId)
                                                                                    .name(name)
                                                                                    .date(date)
                                                                                    .time(time)
                                                                                    .partySize(partySize).build());

        mvc.perform(post("/restaurants/3/reservations")
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"date\":\"2019-12-24\", \"time\":\"20:00\", \"partySize\":20}"))
                    .andExpect(status().isCreated());

        verify(reservationService).addReservation(restaurantId, userId, name, date, time, partySize);
    }
}