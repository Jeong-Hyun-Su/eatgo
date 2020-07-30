package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RestaurantService {
	private final RestaurantRepository restaurantRepository;

	@Autowired
	public RestaurantService(RestaurantRepository restaurantRepository) {
		this.restaurantRepository = restaurantRepository;
	}
	public List<Restaurant> getRestaurants() {
		return restaurantRepository.findAll();
	}
	public List<Restaurant> getRestaurants(String region, Integer categoryId) {
		return restaurantRepository.findAllByCityContainingAndCategoryId(region, categoryId);
	}
	public List<Restaurant> getRestaurants(Integer categoryId) {
		return restaurantRepository.findAllByCategoryId(categoryId);
	}
	public List<Restaurant> getRestaurants(String region) {
		return restaurantRepository.findAllByCity(region);
	}

	public Restaurant getRestaurant(Integer id) {
		Restaurant restaurant = restaurantRepository.findById(id)
													.orElseThrow(() -> new RestaurantNotFoundException(id));
		return restaurant;
	}

	public Restaurant addRestaurant(Restaurant restaurant) {
		return restaurantRepository.save(restaurant);
	}

	@Transactional
	public void updateRestaurant(Integer id, String name, String city) {
		Restaurant restaurant = restaurantRepository.findById(id).orElse(null);

		restaurant.updateInformation(name, city);
	}
}
