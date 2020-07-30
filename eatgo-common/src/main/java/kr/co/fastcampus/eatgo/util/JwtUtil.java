package kr.co.fastcampus.eatgo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JwtUtil {
    private Key keys;

    public JwtUtil(String keyString){
        // 32자리 맞춰야함 - 특정 문자열에 대한 암호화 서명 키를 제작
        this.keys = Keys.hmacShaKeyFor(keyString.getBytes());
    }

    // Token을 돌려주기 위한 함
    public String createToken(Integer id, String name, Integer restaurantId) {
        // Claim 권한을 설정하여 signWith로 HS256알고리즘으로 서명한 후 토큰을 제작.
        JwtBuilder builder = Jwts.builder()
                .claim("id", id)
                .claim("name", name);

        if(restaurantId != null){
            builder = builder.claim("restaurantId", restaurantId);
        }

        return builder.signWith(keys, SignatureAlgorithm.HS256)
                      .compact();
    }

    // 토큰을 받고 권한(CLaim)을 돌려주는 함수
    public Claims getClaims(String token){
        return Jwts.parserBuilder()
                //감지 된 JWS 디지털 서명을 확인하는 데 사용되는 서명 키를 설정
                .setSigningKey(keys)
                .build()
                //사용자로부터 받은 토큰을 풀어서 Claim에 대한 내용 Body를 돌려줌.
                .parseClaimsJws(token)
                .getBody();
    }
}
