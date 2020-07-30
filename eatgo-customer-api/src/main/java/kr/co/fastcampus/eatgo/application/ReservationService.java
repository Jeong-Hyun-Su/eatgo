package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.Reservation;
import kr.co.fastcampus.eatgo.domain.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ReservationService {

    ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;

    }

    public Reservation addReservation(Integer restaurantId, Integer userId, String name, String date, String time, Integer partySize) {
        return reservationRepository.save(Reservation.builder().restaurantId(restaurantId)
                                    .userId(userId)
                                    .name(name)
                                    .date(date)
                                    .time(time)
                                    .partySize(partySize).build());
    }
}
