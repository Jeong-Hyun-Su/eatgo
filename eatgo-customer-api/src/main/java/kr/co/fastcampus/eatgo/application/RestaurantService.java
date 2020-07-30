package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RestaurantService {
	private final RestaurantRepository restaurantRepository;
	private final MenuItemRepository menuItemRepository;
	private final ReviewRepository reviewRepository;

	@Autowired
	public RestaurantService(RestaurantRepository restaurantRepository, MenuItemRepository menuItemRepository, ReviewRepository reviewRepository) {
		this.restaurantRepository = restaurantRepository;
		this.menuItemRepository = menuItemRepository;
		this.reviewRepository = reviewRepository;
	}

	public List<Restaurant> getRestaurants() {
		List<Restaurant> restaurants = restaurantRepository.findAll();
		
		return restaurants;
	}

	public Restaurant getRestaurant(Integer id) {
		Restaurant restaurant = restaurantRepository.findById(id)
													.orElseThrow(() -> new RestaurantNotFoundException(id));
		
		List<MenuItem> menuItems = menuItemRepository.findAllByRestaurantId(id);
		restaurant.setMenuItems(menuItems);

		List<Review> reviews = reviewRepository.findAllByRestaurantId(id);
		restaurant.setReviews(reviews);

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
