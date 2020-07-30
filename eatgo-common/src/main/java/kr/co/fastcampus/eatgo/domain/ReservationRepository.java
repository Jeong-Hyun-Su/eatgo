package kr.co.fastcampus.eatgo.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReservationRepository extends CrudRepository<Reservation, Integer> {
    Reservation save(Reservation reservation);

    List<Reservation> findAllByRestaurantId(Integer restaurantId);
}
