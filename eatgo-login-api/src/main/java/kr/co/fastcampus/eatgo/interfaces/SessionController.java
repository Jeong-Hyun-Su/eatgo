package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.UserService;
import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class SessionController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    // 해당 유저에 해당하는 고유 토큰을 만들어서 돌려줌.
    @PostMapping("/session")
    public ResponseEntity<SessionResponseDto> create(@RequestBody SessionRequestDto resource)
                                    throws URISyntaxException {
        // 일치하는 Email과 Password가 있을 시, 유저를 반환
        User user = userService.authenticate(resource.getEmail(), resource.getPassword());

        // User의 정보를 바탕으로 토큰을 생성
        String accessToken = jwtUtil.createToken(user.getId(), user.getName(), user.isRestaurantOwner() ? user.getRestaurantId() : null);
        String uri = "/session/" + user.getId();
        return ResponseEntity.created(new URI(uri))
                             .body(SessionResponseDto.builder()
                                             .accessToken(accessToken).build());
    }
}
