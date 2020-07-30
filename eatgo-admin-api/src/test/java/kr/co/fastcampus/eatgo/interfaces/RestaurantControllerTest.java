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
	
	// MockBean
	@MockBean
	private RestaurantService restaurantService;
	
	/* SpyBean
	 
	@SpyBean(RestaurantService.class)
	private RestaurantService restaurantService;
	
	@SpyBean(RestaurantRepositoryImpl.class)
	private RestaurantRepository restaurantRepository;
	
	@SpyBean(MenuItemRepositoryImpl.class)
	private MenuItemRepository menuItemRepository;
	*/
	
	@Test
	public void list() throws Exception {
		// ======================= 
		// MockBean
		List<Restaurant> restaurants = new ArrayList<>();
		restaurants.add(Restaurant.builder().id(1004).name("Jeong").city("Seoul").categoryId(1).build());
		restaurants.add(Restaurant.builder().id(2222).name("Hyeon").city("Seoul").categoryId(1).build());
		given(restaurantService.getRestaurants("Seoul", 1)).willReturn(restaurants);

		// =======================
		mvc.perform(get("/restaurants?region=Seoul&category=1"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("\"id\":1004")))
			.andExpect(content().string(containsString("\"name\":\"Jeong\"")))
			.andExpect(content().string(containsString("\"name\":\"Hyeon\"")))
			.andExpect(content().string(containsString("\"city\":\"Seoul\"")))
			.andExpect(content().string(containsString("\"categoryId\":1")));

	}
	
	@Test
	public void detailWithExisted() throws Exception{
		// ======================= 
		// MockBean
		Restaurant restaurant = Restaurant.builder()
											.id(2213)
											.name("Hyeon")
											.city("Suncheon")
											.categoryId(1)
											.build();


		given(restaurantService.getRestaurant(2213)).willReturn(restaurant);
		// =======================
		mvc.perform(get("/restaurants/2213"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("\"id\":2213")))
			.andExpect(content().string(containsString("\"name\":\"Hyeon\"")))
			.andExpect(content().string(containsString("\"city\":\"Suncheon\"")));
	}
	
	@Test
	public void detailWithNotExisted() throws Exception{
		given(restaurantService.getRestaurant(404)).willThrow(new RestaurantNotFoundException(404));
		mvc.perform(get("/restaurants/404"))
					.andExpect(status().isNotFound())
					.andExpect(content().string("{}"));
	}
	
	@Test
	public void create() throws Exception{
		given(restaurantService.addRestaurant(ArgumentMatchers.any())).will(invocation ->{
			Restaurant restaurant = invocation.getArgument(0);
			return Restaurant.builder()
					.id(9999)
					.name(restaurant.getName())
					.city(restaurant.getCity())
					.categoryId(1)
					.build();
		});
		
		mvc.perform(post("/restaurants")
					 .contentType(MediaType.APPLICATION_JSON)
					 .content("{\"name\":\"Hyeon\",\"city\":\"Busan\", \"categoryId\":1}"))
			.andExpect(status().isCreated())
			.andExpect(header().string("location", "/restaurants/9999"))
			.andExpect(content().string("{}"));
		
		verify(restaurantService, times(1)).addRestaurant(ArgumentMatchers.any());
	}


	@Test
	public void createWithInvalidData() throws Exception{
		mvc.perform(post("/restaurants")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"\",\"city\":\"\", \"categoryId\":}"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void update() throws Exception {
		mvc.perform(patch("/restaurants/1004")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"name\":\"Hyeon\", \"city\":\"Busan\", \"categoryId\":1}"))
					.andExpect(status().isOk());

		verify(restaurantService).updateRestaurant(1004, "Hyeon", "Busan");

	}

	@Test
	public void updateWithInvalidData() throws Exception {
		mvc.perform(patch("/restaurants/1004")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"name\":\"\", \"city\":\"\", \"categoryId\":}"))
					.andExpect(status().isBadRequest());
	}
}
