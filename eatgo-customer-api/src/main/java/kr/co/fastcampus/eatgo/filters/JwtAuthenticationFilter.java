package kr.co.fastcampus.eatgo.filters;

import io.jsonwebtoken.Claims;
import kr.co.fastcampus.eatgo.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 서버에 요청할 때, Header로부터 토큰 키를 받게 되는데 Filter를 통해 사용자이면 권한을 부여
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
    private JwtUtil jwtUtil;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
    }

    // Filter - 사용자로부터 request를 받음.
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        // 인증(Authenticate) : 현재 유저가 누구인지 확인된 것을 얻어
        Authentication authentication = getAuthentication(request);
        if(authentication != null){
            //spring security의 인메모리 세션저장소인 SecurityContextHolder 에 저장
            //이후 요청에서는 요청쿠키에서 JSESSIONID를 까봐서 검증 후 유효하면 Authentication를 쥐어준다.
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request,response);
    }

    // 사용자의 요청 중, Header "Authorization"을 가져와 Claim(정보)를 얻어오고
    //
    private Authentication getAuthentication(HttpServletRequest request){
        String token = request.getHeader("Authorization");

        if(token == null){
            return null;
        }

        // JwtUtil에서 Claims 얻기
        Claims claims = jwtUtil.getClaims(token.substring("Bearer ".length()));

        //아이디, 패스워드를 이용한 인증을 담당하는 필터를 통해 Authentication(인증)을 돌려줌.
        Authentication authentication = new UsernamePasswordAuthenticationToken(claims, null);
        return authentication;
    }
}
