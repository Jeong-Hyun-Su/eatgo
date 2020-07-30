package kr.co.fastcampus.eatgo.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends CrudRepository<Restaurant, Integer>{

	List<Restaurant> findAll();
	List<Restaurant> findAllByCityContainingAndCategoryId(String region, Integer categoryId);
	List<Restaurant> findAllByCategoryId(Integer categoryId);
	List<Restaurant> findAllByCity(String region);

	Optional<Restaurant> findById(Integer id);

	Restaurant save(Restaurant restaurant);
}