package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.RegionService;
import kr.co.fastcampus.eatgo.domain.Region;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RegionController.class)
class RegionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RegionService regionService;

    @Test
    public void list() throws Exception{
        List<Region> regions = new ArrayList<>();
        regions.add(Region.builder().name("Seoul").build());

        given(regionService.getRegions()).willReturn(regions);

        mvc.perform(get("/regions"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("Seoul")));
    }

    @Test
    public void create() throws Exception{
        Region region = Region.builder().name("Seoul").build();
        given(regionService.addRegion("Seoul")).willReturn(region);

        mvc.perform(post("/regions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"name\":\"Seoul\"}"))
                    .andExpect(status().isCreated())
                    .andExpect(content().string("{}"));

        verify(regionService, times(1)).addRegion("Seoul");
    }

}