package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.Reservation;
import kr.co.fastcampus.eatgo.domain.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class ReservationServiceTest {
    private ReservationService restaurantService;

    @Mock
    private ReservationRepository reservationRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        restaurantService = new ReservationService(reservationRepository);
    }

    @Test
    public void getReservations(){
        List<Reservation> reservations = restaurantService.getReservations(11);

        verify(reservationRepository).findAllByRestaurantId(11);
    }
}