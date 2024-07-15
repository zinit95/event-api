package com.study.event.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// 스프링 시큐리티 설정 파일
// 인터셉터, 필터 처리
// 세션인증, 토큰인증
// 권한처리
// OAuth2 - SNS로그인
@EnableWebSecurity
public class SecurityConfig {

    // 비밀번호 암호화 객체 컨테이너에 등록 (스프링에게 주입받는 설정)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 시큐리티 설정 (스프링 부트 2.7버전 이전 인터페이스를 통해 오버라이딩)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception {

        http
                .cors()
                .and()
                .csrf().disable() // 필터설정 off
                .httpBasic().disable() // 베이직 인증 off
                .formLogin().disable() // 로그인창 off
                // 세션 인증은 더 이상 사용하지 않음
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests() // 요청 별로 인가 설정

                // 아래의 URL요청은 로그인 없이 모두 허용
                .antMatchers("/", "/auth/**").permitAll() //"/", "/auth/**" 로 들어오는거는 인증 안받아도 됨
//                .antMatchers(HttpMethod.POST,"/events/**").permitAll()

                // 나머지 요청은 전부 인증(로그인) 후 진행해라
                //.anyRequest().permitAll() // 모든 HTTP 요청에 대해 접근을 허용
                .anyRequest().authenticated() // 인가 설정 on
        ;

        return http.build();
    }



}
