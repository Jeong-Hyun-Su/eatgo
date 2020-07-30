package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.Region;
import kr.co.fastcampus.eatgo.domain.RegionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class RegionServiceTest {
    private RegionService regionService;

    @Mock
    RegionRepository regionRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        regionService = new RegionService(regionRepository);
    }

    @Test
    public void getRegions(){
        List<Region> mockRegions = new ArrayList<>();
        mockRegions.add(Region.builder().name("Seoul").build());

        given(regionRepository.findAll()).willReturn(mockRegions);

        List<Region> regions = regionService.getRegions();
        Region region = regions.get(0);

        assertThat(region.getName()).isEqualTo("Seoul");
    }

    @Test
    public void addRegion(){
        given(regionRepository.save(any())).will(invocation -> {
           Region region = invocation.getArgument(0);
           return region;
        });

        Region region = regionService.addRegion("Seoul");
        verify(regionRepository, times(1)).save(any());

        assertThat(region.getName()).isEqualTo("Seoul");
    }
}