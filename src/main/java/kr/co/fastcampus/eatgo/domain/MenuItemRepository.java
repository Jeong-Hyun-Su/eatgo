package kr.co.fastcampus.eatgo.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface MenuItemRepository extends CrudRepository<MenuItem, Integer>{
	List<MenuItem> findAllByRestaurantId(Integer restaurantId);
	MenuItem save(MenuItem menuItem);
}
