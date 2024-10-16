package com.example.Portfolio_Onboard.Config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Log4j2
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        log.warn("SecurityFilterChain");

        //http.cors(AbstractHttpConfigurer::disable).csrf(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests( authorize -> authorize // 권한부여

                .requestMatchers("/", "/board/**", "/join/**", "/world/**",
                        "/css/**", "/js/**", "/img/**", "/webjars/**", "/login_proc/**").permitAll() // 누구든지 접속 가능
                .requestMatchers("/createBoard/**").hasRole("USER") // USER는 "/createBoard" 접속 가능
                .requestMatchers("/notice/**").hasRole("MANAGER") // MANAGER는 "/notice" 접속 가능
                .anyRequest().authenticated()
        );

        http.formLogin(login -> login

                .loginPage("/index")
                .loginProcessingUrl("/login_proc")
                .defaultSuccessUrl("/index", true) // 로그인 성공 후 이동할 페이지
                .failureUrl("/index?error=true") // 로그인 실패 후 출력 url
                .permitAll()
        );

        http.logout(logout -> logout

                .logoutUrl("/index/logout") // 로그아웃 URL 지정
                .logoutSuccessUrl("/index") // 로그아웃 성공 후 리다이렉트할 URL
                .invalidateHttpSession(true) // 세션 무효화
                .clearAuthentication(true) // 인증 정보 삭제
                .deleteCookies("JSESSIONID") // 쿠키 삭제
                .permitAll() // 모든 사용자에게 로그아웃 URL 접근 허용
        );

        return http.build();
    }
}
