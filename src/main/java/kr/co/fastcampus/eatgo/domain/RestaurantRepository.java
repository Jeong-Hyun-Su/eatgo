package kr.co.fastcampus.eatgo.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface RestaurantRepository extends CrudRepository<Restaurant, Integer>{

	List<Restaurant> findAll();

	Optional<Restaurant> findById(Integer id);

	Restaurant save(Restaurant restaurant);
}