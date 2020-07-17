package kr.co.fastcampus.eatgo.interfaces;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.Restaurant;

@CrossOrigin
@RestController
public class RestaurantController {
	@Autowired
	private RestaurantService restaurantService;
	
	@GetMapping("/restaurants")
	public List<Restaurant> list(){
		List<Restaurant> restaurants = restaurantService.getRestaurants();
		return restaurants;
	}
	
	@GetMapping("/restaurants/{id}")
	public Restaurant detail(@PathVariable("id") Integer id) {
		Restaurant restaurant = restaurantService.getRestaurant(id);	
		
		return restaurant;
	}
	
	@PostMapping("/restaurants")
	public ResponseEntity<?> create(@Valid @RequestBody Restaurant resource) 
			throws URISyntaxException {
		Restaurant restaurant = restaurantService.addRestaurant(Restaurant.builder()
																		.name(resource.getName())
																		.city(resource.getCity())
																		.build());
		
		URI location = new URI("/restaurants/" + restaurant.getId());
		return ResponseEntity.created(location).body("{}");
	}
	
	// @Valid - Validation을 적용한 변수에 대해 검사
	// @RequestBody - Post, Patch 때, 넘겨받은 데이터
	
	@PatchMapping("/restaurants/{id}")
	public String update(@PathVariable("id") Integer id,@Valid @RequestBody Restaurant restaurant) {
		restaurantService.updateRestaurant(id, restaurant.getName(), restaurant.getCity());
		return "{}";
	}
}
