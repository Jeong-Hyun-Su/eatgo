package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.MenuItemService;
import kr.co.fastcampus.eatgo.domain.MenuItem;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MenuItemController.class)
class MenuItemControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private MenuItemService menuItemService;

	@Test
	void getMenuItem() throws Exception{
		List<MenuItem> menuItems = new ArrayList<>();
		menuItems.add(MenuItem.builder().name("GukBab").build());
		menuItems.add(MenuItem.builder().name("Rice").build());

		given(menuItemService.getMenuItems(1)).willReturn(menuItems);
		mvc.perform(get("/restaurants/1/menuitems"))
					.andExpect(status().isOk())
					.andExpect(content().string(containsString("GukBab")))
					.andExpect(content().string(containsString("Rice")));

		verify(menuItemService, times(1)).getMenuItems(eq(1));
	}

	@Test
	void bulkUpdate() throws Exception{
		mvc.perform(patch("/restaurants/1/menuitems")
					.contentType(MediaType.APPLICATION_JSON)
					.content("[]"))
					.andExpect(status().isOk());
					
		verify(menuItemService).bulkUpdate(Mockito.eq(1), ArgumentMatchers.any());
	}

}
