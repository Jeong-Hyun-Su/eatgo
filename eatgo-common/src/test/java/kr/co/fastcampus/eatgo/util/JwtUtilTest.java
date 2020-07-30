package kr.co.fastcampus.eatgo.util;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JwtUtilTest {
    private final String SECRET = "12345678901234567890123456789012";
    @Test
    public void createToken(){
        JwtUtil jwtUtil = new JwtUtil(SECRET);

        String token = jwtUtil.createToken(1004, "John", null);

        assertThat(token).contains(".");
    }

    @Test
    public void getClaims(){
        JwtUtil jwtUtil = new JwtUtil(SECRET);
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MTEsIm5hbWUiOiJUZXN0ZXIifQ.Zg_V-n_Aodcx6NG_eQOgdRUDWS6bwa_tPI_qH4vvFps";

        Claims claims = jwtUtil.getClaims(token);
        assertThat(claims.get("name")).isEqualTo("Tester");
        assertThat(claims.get("id")).isEqualTo(11);
    }
}