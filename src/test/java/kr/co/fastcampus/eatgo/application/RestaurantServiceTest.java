package kr.co.fastcampus.eatgo.application;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import kr.co.fastcampus.eatgo.domain.MenuItem;
import kr.co.fastcampus.eatgo.domain.MenuItemRepository;
import kr.co.fastcampus.eatgo.domain.Restaurant;
import kr.co.fastcampus.eatgo.domain.RestaurantNotFoundException;
import kr.co.fastcampus.eatgo.domain.RestaurantRepository;

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
	}
	
	private void mockRestaurantRepository() {
		List<Restaurant> restaurants = new ArrayList<>();
		List<MenuItem> menuItems = new ArrayList<>();
		menuItems.add(MenuItem.builder().name("GukBab").build());
		menuItems.add(MenuItem.builder().name("Gimchi").build());
		
		Restaurant restaurant = Restaurant.builder()
										.id(1004)
										.name("Hyeon")
										.city("SWW")
										.menuItems(menuItems)
										.build();
		
		restaurants.add(restaurant);
		restaurants.add(Restaurant.builder()
									.id(5121)
									.name("Jeong")
									.city("Seoul")
									.menuItems(new ArrayList<MenuItem>())
									.build());
		
		
		//getRestaurants() 함수
		BDDMockito.given(restaurantRepository.findAll())
					.willReturn(restaurants);	
		//getRestaurant() 함수
		BDDMockito.given(restaurantRepository.findById(1004))
					.willReturn(Optional.of(restaurant));
		
		BDDMockito.given(menuItemRepository.findAllByRestaurantId(1004)).willReturn(menuItems);
		restaurantService = new RestaurantService(restaurantRepository, menuItemRepository);
	}
	
	private void mockMenuItemRepository() {
		List<MenuItem> menuItems = new ArrayList<>();
		menuItems.add(MenuItem.builder().name("GimChi").build());
		
		BDDMockito.given(menuItemRepository.findAllByRestaurantId(1004))
					.willReturn(menuItems);
	}
	
	@DisplayName("getRestaurants")
	@Test
	public void getRestaurants() {
		//given
		List<Restaurant> restaurants = restaurantService.getRestaurants();
		//when
		Restaurant restaurant = restaurants.get(1);
		
		//then
		assertThat(restaurant.getId()).isEqualTo(5121);
	}

	@DisplayName("getRestaurant 데이터O")
	@Test
	public void getRestaurantWithExisted() {
		//given-when
		Restaurant restaurant = restaurantService.getRestaurant(1004);
		List<MenuItem> menuItems = menuItemRepository.findAllByRestaurantId(1004);
		
		//then
		assertThat(restaurant.getId()).isEqualTo(1004);
		assertThat(menuItems.get(0).getName()).isEqualTo("GukBab");
		//		asserTaht();
	}
	
	
	@DisplayName("getRestaurant 데이터X")
	@Test
	public void getRestaurantWithNotExisted() {
		Throwable e = assertThrows(RestaurantNotFoundException.class, () ->{
			throw new RestaurantNotFoundException(404);
		});
		restaurantService.getRestaurant(1004);
		
		assertEquals("Could not find restaurant 404", e.getMessage());
	}
	
	@Test
	public void addRestaurant() {
		
		// restaurantRepository.save를 통해 입력된 ArgumentMatchers.any() 중
		// save는 인자가 1개이기에, invocation.getArgument(0)은 Restaurant 밖에 없음.
		// 따라서 입력된 invocation (Restaurant)를 가져와 Id를 1234로 설정하고 다시 돌려줌.

		//given
		BDDMockito.given(restaurantRepository.save(ArgumentMatchers.any())).will(invocation ->{
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
		BDDMockito.given(restaurantRepository.findById(1004)).willReturn(Optional.of(restaurant));
		
		//when
		restaurantService.updateRestaurant(1004, "JJJJ", "WHATT");
		//then
		assertThat(restaurant.getName()).isEqualTo("JJJJ");
		assertThat(restaurant.getCity()).isEqualTo("WHATT");
	}
}
