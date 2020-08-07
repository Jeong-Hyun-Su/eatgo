package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.Restaurant;
import kr.co.fastcampus.eatgo.domain.RestaurantVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
	@Autowired
	private RestaurantService restaurantService;

	@GetMapping
	public List<Restaurant> AllList(){
		return restaurantService.getRestaurants();
	}

	@GetMapping("/detail")
	public List<Restaurant> CategoryOrRegionList(RestaurantVO restaurantVO) throws Exception{
		Integer category = restaurantVO.getCategory();
		String region = restaurantVO.getRegion();

		if(category == null)
			return restaurantService.getRestaurants(region);
		else if(region == null)
			return restaurantService.getRestaurants(category);

		return restaurantService.getRestaurants(region, category);
	}

	@GetMapping("/{id}")
	public Restaurant detail(@PathVariable("id") Integer id) {
		return restaurantService.getRestaurant(id);
	}

	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody Restaurant resource)
			throws URISyntaxException {
		Restaurant restaurant = restaurantService.addRestaurant(Restaurant.builder()
				.name(resource.getName())
				.city(resource.getCity())
				.categoryId((resource.getCategoryId()))
				.build());

		URI location = new URI("/restaurants/" + restaurant.getId());
		return ResponseEntity.created(location).body("{}");
	}

	// @Valid - Validation을 적용한 변수에 대해 검사
	// @RequestBody - Post, Patch 때, 넘겨받은 데이터
	@PatchMapping("/{id}")
	public String update(@PathVariable("id") Integer id,@Valid @RequestBody Restaurant restaurant) {
		restaurantService.updateRestaurant(id, restaurant.getName(), restaurant.getCity());
		return "{}";
	}
}
