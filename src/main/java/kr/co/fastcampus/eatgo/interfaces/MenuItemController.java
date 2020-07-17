package kr.co.fastcampus.eatgo.interfaces;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.fastcampus.eatgo.application.MenuItemService;
import kr.co.fastcampus.eatgo.domain.MenuItem;

@RestController
public class MenuItemController {
	@Autowired
	private MenuItemService menuItemService;
	
	@PatchMapping("/restaurants/{restaurantId}/menuitems")
	public String bulkUpdate(
						@RequestBody List<MenuItem> menuItems,
						@PathVariable("restaurantId") Integer restaurantId) {
		menuItemService.bulkUpdate(restaurantId, menuItems);
		return "";
	}
}
