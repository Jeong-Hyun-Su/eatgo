package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.MenuItem;
import kr.co.fastcampus.eatgo.domain.Restaurant;
import kr.co.fastcampus.eatgo.domain.RestaurantNotFoundException;
import kr.co.fastcampus.eatgo.domain.Review;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private RestaurantService restaurantService;

	@Test
	public void list() throws Exception {
		// ======================= 
		// MockBean
		List<Restaurant> restaurants = new ArrayList<>();
		restaurants.add(Restaurant.builder().id(1004).name("Jeong").city("Seoul").build());
		restaurants.add(Restaurant.builder().id(2222).name("Hyeon").city("Suncheon").build());
		given(restaurantService.getRestaurants()).willReturn(restaurants);
		// =======================
		mvc.perform(get("/restaurants"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("\"id\":1004")))
			.andExpect(content().string(containsString("\"name\":\"Jeong\"")))
			.andExpect(content().string(containsString("\"city\":\"Seoul\"")));
	}
	
	@Test
	public void detailWithExisted() throws Exception{
		// ======================= 
		// MockBean
		Restaurant restaurant = Restaurant.builder()
											.id(2213)
											.name("Hyeon")
											.city("Suncheon")
											.build();

		MenuItem menuItem = MenuItem.builder()
									.name("KimChi")
									.build();
		restaurant.setMenuItems(Arrays.asList(menuItem));

		Review review = Review.builder()
							  .name("JOKER")
							  .score(5)
							  .description("Great!")
							  .build();
		restaurant.setReviews(Arrays.asList(review));

		given(restaurantService.getRestaurant(2213)).willReturn(restaurant);
		// =======================
		mvc.perform(get("/restaurants/2213"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("\"id\":2213")))
			.andExpect(content().string(containsString("\"name\":\"Hyeon\"")))
			.andExpect(content().string(containsString("\"city\":\"Suncheon\"")))
			.andExpect(content().string(containsString("KimChi")))
			.andExpect(content().string(containsString("Great!")));
	}
	
	@Test
	public void detailWithNotExisted() throws Exception{
		given(restaurantService.getRestaurant(404)).willThrow(new RestaurantNotFoundException(404));
		mvc.perform(get("/restaurants/404"))
					.andExpect(status().isNotFound())
					.andExpect(content().string("{}"));
	}
}
