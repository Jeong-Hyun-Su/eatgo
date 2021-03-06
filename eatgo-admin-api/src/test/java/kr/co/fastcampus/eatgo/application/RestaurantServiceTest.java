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
	}

	private void mockRestaurantRepository() {
		List<Restaurant> restaurants = new ArrayList<>();

		Restaurant restaurant = Restaurant.builder()
										.id(1004)
										.name("Hyeon")
										.city("Seoul")
										.categoryId(1)
										.menuItems(new ArrayList<MenuItem>())
										.build();

		restaurants.add(restaurant);
		restaurants.add(Restaurant.builder()
									.id(5121)
									.name("Jeong")
									.city("Seoul")
									.categoryId(3)
									.menuItems(new ArrayList<MenuItem>())
									.build());



		//getRestaurants() 함수
		given(restaurantRepository.findAllByCityContainingAndCategoryId("Seoul", 1))
					.willReturn(restaurants);
		//getRestaurant() 함수
		given(restaurantRepository.findById(1004))
					.willReturn(Optional.of(restaurant));

		restaurantService = new RestaurantService(restaurantRepository);
	}

	@DisplayName("getRestaurants")
	@Test
	public void getRestaurants() {
		//given
		List<Restaurant> restaurants = restaurantService.getRestaurants("Seoul", 1);

		Restaurant restaurant = restaurants.get(1);

		//then
		assertThat(restaurant.getId()).isEqualTo(5121);
	}

	@DisplayName("getRestaurant 데이터O")
	@Test
	public void getRestaurantWithExisted() {
		//given-when`
		Restaurant restaurant = restaurantService.getRestaurant(1004);
		assertThat(restaurant.getId()).isEqualTo(1004);
	}
	
	
	@DisplayName("getRestaurant 데이터X")
	@Test
	public void getRestaurantWithNotExisted() {
		Throwable e = assertThrows(RestaurantNotFoundException.class, () ->{
			restaurantService.getRestaurant(404);
		});

		assertEquals("Could not find restaurant 404", e.getMessage());
	}
	
	@Test
	public void addRestaurant() {
		
		// restaurantRepository.save를 통해 입력된 ArgumentMatchers.any() 중
		// save는 인자가 1개이기에, invocation.getArgument(0)은 Restaurant 밖에 없음.
		// 따라서 입력된 invocation (Restaurant)를 가져와 Id를 1234로 설정하고 다시 돌려줌.

		//given

		given(restaurantRepository.save(any())).will(invocation ->{
			Restaurant restaurant = invocation.getArgument(0);
			restaurant.setId(1234);
			return restaurant;
		});
		
		Restaurant restaurant = Restaurant.builder()
										.name("BeRyong")
										.city("GangGang")
										.build();
		
		//when
		restaurantService.addRestaurant(restaurant);
		//then
		assertThat(restaurant.getId()).isEqualTo(1234);
		
	}
	
	@Test
	public void updateRestaurant() {
		//given
		Restaurant restaurant = Restaurant.builder()
				.id(1004)
				.name("Hyeon")
				.city("Busan")
				.build();
				
		// findById 는 Optional<Restaurant> 형태로 돌려주기에 willReturn 값도 Optional.of로 자료형을 맞추어 돌려준다.
		given(restaurantRepository.findById(1004)).willReturn(Optional.of(restaurant));
		
		//when
		restaurantService.updateRestaurant(1004, "JJJJ", "WHATT");
		//then
		assertThat(restaurant.getName()).isEqualTo("JJJJ");
		assertThat(restaurant.getCity()).isEqualTo("WHATT");
	}
}
