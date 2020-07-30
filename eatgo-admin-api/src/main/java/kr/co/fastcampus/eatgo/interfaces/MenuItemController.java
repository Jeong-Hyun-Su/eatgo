package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.MenuItemService;
import kr.co.fastcampus.eatgo.domain.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MenuItemController {
	@Autowired
	private MenuItemService menuItemService;

	@GetMapping("/restaurants/{restaurantId}/menuitems")
	public List<MenuItem> getMenuItem(@PathVariable("restaurantId") Integer restaurantId){
		return menuItemService.getMenuItems(restaurantId);
	}

	@PatchMapping("/restaurants/{restaurantId}/menuitems")
	public String bulkUpdate(
						@RequestBody List<MenuItem> menuItems,
						@PathVariable("restaurantId") Integer restaurantId) {
		menuItemService.bulkUpdate(restaurantId, menuItems);
		return "";
	}
}
