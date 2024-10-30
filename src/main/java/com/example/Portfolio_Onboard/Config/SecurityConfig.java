package com.example.Portfolio_Onboard.Config;

import jakarta.servlet.http.HttpServletResponse;
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

                .requestMatchers("/", "/board/**", "/world/**", "/post/**", "/comment_proc/**", "/childcomment_proc/**",
                        "/createPost/**", "/createPost_proc/**", "/join/**", "/join_proc/**", "/login_proc/**",
                        "/childCommentDel/**", "/commentDel/**", "/postDel/**", "/boardDel/**", "/modifyPost/**", "/modifyPost_proc/**",
                        "/postModifyPwdCheck/**",
                        "/css/**", "/js/**", "/img/**", "/webjars/**").permitAll() // 누구든지 접속 가능
                .requestMatchers("/createBoard/**").hasRole("USER") // USER는 "/createBoard" 접속 가능
                .requestMatchers("/notice/**").hasRole("MANAGER") // MANAGER는 "/notice" 접속 가능
                .anyRequest().authenticated()
        );

        http.formLogin(login -> login // 로그인 요청 처리 시 HTTP 응답 상태 코드를 설정하는 역할을 합니다.
                // 그리고 HTTP 응답 상태를 ajax의 response 변수에 전달하고 그것을 바탕으로 다양한 동작을 실행합니다.

                .loginPage("/index")
                .loginProcessingUrl("/login_proc")
                .defaultSuccessUrl("/index", true) // 로그인 성공 후 이동할 페이지
                //.failureUrl("/index?error=true") // 로그인 실패 후 출력 url
                .failureHandler((request, response, exception) -> {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    // 실패 시 401 반환하고 어떠한 세션도 생성하지 않는다.
                })
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
