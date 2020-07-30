package kr.co.fastcampus.eatgo.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MenuItemRepository extends CrudRepository<MenuItem, Integer>{
	List<MenuItem> findAllByRestaurantId(Integer restaurantId);

	void deleteById(Integer id);
}
