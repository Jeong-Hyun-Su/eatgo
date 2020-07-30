package kr.co.fastcampus.eatgo.application;


import kr.co.fastcampus.eatgo.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class RestaurantServiceTest {

	private RestaurantService restaurantService;
	/* SpyBean
	private RestaurantRepository restaurantRepository;
	private MenuItemRepository menuItemRepository;
	*/
	
	@Mock
	private RestaurantRepository restaurantRepository;
	@Mock
	private MenuItemRepository menuItemRepository;
	@Mock
	private ReviewRepository reviewRepository;

	@BeforeEach
	public void setUp() {
		/* SpyBean
		restaurantRepository = new RestaurantRepositoryImpl();
		menuItemRepository = new MenuItemRepositoryImpl();
		*/
		
		// Mock 으로 잡아주긴 했지만, 제대로 된 Mock 객체가 할당이 되어있지 않음
		// @Mock 어노테이션이 붙은 것들을 초기화해주는 역할을 함. (this는 모든 객체들)
		MockitoAnnotations.initMocks(this);		
		
		mockRestaurantRepository();
		mockMenuItemRepository();
		mockReviewRepository();
	}

	private void mockRestaurantRepository() {
		List<Restaurant> restaurants = new ArrayList<>();

		Restaurant restaurant = Restaurant.builder()
										.id(1004)
										.name("Hyeon")
										.city("SWW")
										.menuItems(new ArrayList<MenuItem>())
										.build();

		restaurants.add(restaurant);
		restaurants.add(Restaurant.builder()
									.id(5121)
									.name("Jeong")
									.city("Seoul")
									.menuItems(new ArrayList<MenuItem>())
									.build());

		//getRestaurants() 함수
		given(restaurantRepository.findAll())
					.willReturn(restaurants);
		//getRestaurant() 함수
		given(restaurantRepository.findById(1004))
					.willReturn(Optional.of(restaurant));

		restaurantService = new RestaurantService(restaurantRepository, menuItemRepository, reviewRepository);
	}

	private void mockMenuItemRepository() {
		List<MenuItem> menuItems = new ArrayList<>();
		menuItems.add(MenuItem.builder().name("GukBab").build());
		menuItems.add(MenuItem.builder().name("GimChi").build());

		given(menuItemRepository.findAllByRestaurantId(1004))
					.willReturn(menuItems);
	}

	private void mockReviewRepository() {
		List<Review> reviews = new ArrayList<>();
		reviews.add(Review.builder().name("BeRyong").score(1).description("Bad").build());
		given(reviewRepository.findAllByRestaurantId(1004)).willReturn(reviews);
	}

	@DisplayName("getRestaurants")
	@Test
	public void getRestaurants() {
		//given
		List<Restaurant> restaurants = restaurantService.getRestaurants();

		Restaurant restaurant = restaurants.get(1);

		//then
		assertThat(restaurant.getId()).isEqualTo(5121);
	}

	@DisplayName("getRestaurant 데이터O")
	@Test
	public void getRestaurantWithExisted() {
		//given-when
		Restaurant restaurant = restaurantService.getRestaurant(1004);
		verify(menuItemRepository).findAllByRestaurantId(eq(1004));
		verify(reviewRepository).findAllByRestaurantId(eq(1004));
		assertThat(restaurant.getId()).isEqualTo(1004);

		MenuItem menuItem = restaurant.getMenuItems().get(0);
		assertThat(menuItem.getName()).isEqualTo("GukBab");

		Review review = restaurant.getReviews().get(0);
		assertThat(review.getDescription()).isEqualTo("Bad");
	}
	
	
	@DisplayName("getRestaurant 데이터X")
	@Test
	public void getRestaurantWithNotExisted() {
		Throwable e = assertThrows(RestaurantNotFoundException.class, () ->{
			restaurantService.getRestaurant(404);
		});

		assertEquals("Could not find restaurant 404", e.getMessage());
	}
}
