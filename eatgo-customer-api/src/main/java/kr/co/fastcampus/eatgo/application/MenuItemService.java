package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.MenuItem;
import kr.co.fastcampus.eatgo.domain.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {
	private final MenuItemRepository menuItemRepository;
	
	@Autowired
	public MenuItemService(MenuItemRepository menuItemRepository) {
		this.menuItemRepository = menuItemRepository;
	}

	public void bulkUpdate(Integer restaurantId, List<MenuItem> menuItems) {
		for(MenuItem menuItem : menuItems) {
			update(restaurantId, menuItem);
		}
	}

	public void update(Integer restaurantId, MenuItem menuItem) {
		if(menuItem.isDestroy()){
			menuItemRepository.deleteById(menuItem.getId());
			return;
		}

		menuItem.setRestaurantId(restaurantId);
		menuItemRepository.save(menuItem);
	}
}
