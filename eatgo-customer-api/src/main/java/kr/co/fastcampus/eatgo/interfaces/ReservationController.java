package kr.co.fastcampus.eatgo.interfaces;

import io.jsonwebtoken.Claims;
import kr.co.fastcampus.eatgo.application.ReservationService;
import kr.co.fastcampus.eatgo.domain.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @PostMapping("/restaurants/{restaurantId}/reservations")
    public ResponseEntity<?> create(
            Authentication authentication,
            @PathVariable("restaurantId") Integer restaurantId,
            @Valid @RequestBody Reservation resource
            ) throws URISyntaxException {
        Claims claims = (Claims) authentication.getPrincipal();

        String name = claims.get("name", String.class);
        Integer userId = claims.get("id", Integer.class);

        String uri = "/restaurants/" + restaurantId +"/reservations/" + userId;

        reservationService.addReservation(restaurantId, userId, name,
                                            resource.getDate(), resource.getTime(), resource.getPartySize());

        return ResponseEntity.created(new URI(uri)).body("{}");
    }

}
