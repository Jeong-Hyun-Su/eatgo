package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.RegionService;
import kr.co.fastcampus.eatgo.domain.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

@RestController
public class RegionController {
    @Autowired
    private RegionService regionService;

    @GetMapping("/regions")
    public List<Region> list(){
        List<Region> regions = regionService.getRegions();
        return regions;
    }

    @PostMapping("/regions")
    public ResponseEntity<?> create(@RequestBody Region source) throws URISyntaxException {
        regionService.addRegion(source.getName());

        URI url = new URI("/regions/" + source.getId());
        return ResponseEntity.created(url).body("{}");
    }
}
