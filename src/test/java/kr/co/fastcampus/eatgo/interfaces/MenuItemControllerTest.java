package kr.co.fastcampus.eatgo.interfaces;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import kr.co.fastcampus.eatgo.application.MenuItemService;

@WebMvcTest(MenuItemController.class)
class MenuItemControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private MenuItemService menuItemService;
	
	@Test
	void bulkUpdate() throws Exception{
		mvc.perform(patch("/restaurants/1/menuitems")
					.contentType(MediaType.APPLICATION_JSON)
					.content("[]"))
					.andExpect(status().isOk());
					
		verify(menuItemService).bulkUpdate(Mockito.eq(1), ArgumentMatchers.any());
	}

}
