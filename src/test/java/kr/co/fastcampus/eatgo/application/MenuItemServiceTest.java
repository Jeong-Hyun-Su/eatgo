package kr.co.fastcampus.eatgo.application;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import kr.co.fastcampus.eatgo.domain.MenuItem;
import kr.co.fastcampus.eatgo.domain.MenuItemRepository;

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
	void bulkUpdate() {
		List<MenuItem> menuItems = new ArrayList<>();

		menuItems.add(MenuItem.builder().name("GukBab").build());
		menuItems.add(MenuItem.builder().name("GimChi").build());
		
		menuItemService.bulkUpdate(1, menuItems);
	
		verify(menuItemRepository, times(2)).save(ArgumentMatchers.any());
	}

}
