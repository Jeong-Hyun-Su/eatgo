package kr.co.fastcampus.eatgo;

import kr.co.fastcampus.eatgo.filters.JwtAuthenticationFilter;
import kr.co.fastcampus.eatgo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter {
    // application.yml 에 jwt.secret의 값을 가져와 지정.
    @Value("${jwt.secret}")
    private String secret;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        Filter filter = new JwtAuthenticationFilter(authenticationManager(), jwtUtil());

            http.csrf().disable()
                .cors().disable()
                .formLogin().disable()
                // IFrame에 대한 보안 끄기
                .headers().frameOptions().disable()
                .and()
                // Filter 설정
                .addFilter(filter)
                // Session 관리
                .sessionManagement()
                // Session에 대한 정책 설정. State가 필요없음.
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    //Component로 등록되어 있지 않기에 @Bean을 통해 잡아줌.
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //Component로 등록되어 있지 않기에 @Bean을 통해 잡아줌.
    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil(secret);
    }
}
