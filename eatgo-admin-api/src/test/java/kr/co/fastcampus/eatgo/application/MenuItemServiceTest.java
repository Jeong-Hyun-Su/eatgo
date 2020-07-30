package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.MenuItem;
import kr.co.fastcampus.eatgo.domain.MenuItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class MenuItemServiceTest {

	private MenuItemService menuItemService;
	
	@Mock
	private MenuItemRepository menuItemRepository;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		menuItemService = new MenuItemService(menuItemRepository);
	}

	@Test
	void getMenuItems(){
		List<MenuItem> mockMenuItems = new ArrayList<>();
		mockMenuItems.add(MenuItem.builder().name("KimChi").build());

		given(menuItemRepository.findAllByRestaurantId(1004)).willReturn(mockMenuItems);

		List<MenuItem> menuItems = menuItemService.getMenuItems(1004);
		MenuItem menuItem = mockMenuItems.get(0);

		assertThat(menuItem.getName()).isEqualTo("KimChi");
	}
	
	@Test
	void bulkUpdate() {
		List<MenuItem> menuItems = new ArrayList<>();

		menuItems.add(MenuItem.builder().name("GukBab").build());
		menuItems.add(MenuItem.builder().id(14).name("GimChi").build());
		menuItems.add(MenuItem.builder().id(1004).destroy(true).build());

		menuItemService.bulkUpdate(1, menuItems);
	
		verify(menuItemRepository, times(2)).save(ArgumentMatchers.any());
		verify(menuItemRepository, times(1)).deleteById(eq(1004));
	}

}
