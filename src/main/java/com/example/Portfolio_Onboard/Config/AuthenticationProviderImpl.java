package com.example.Portfolio_Onboard.Config;

import com.example.Portfolio_Onboard.Entity.EntityMemberInfo;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
public class AuthenticationProviderImpl implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String userid = (String) authentication.getPrincipal();
        String pwd = (String) authentication.getCredentials();

        UserDetails user = userDetailsService.loadUserByUsername(userid);


        if (user == null) {

            throw new UsernameNotFoundException("아이디가 일치하지 않습니다.");
        }

        if (!pwd.equals(user.getPassword())) {

            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }
        // 위 코드를 try catch 에 넣어버리면 Exception 이 SecurityConfig 로 전송되지 않는다.
        // catch 문이 SecurityConfig 로 전송될 Exception 을 잡아버리기 때문에 아이디만 일치하면 로그인되는 에러 발생.
        /* Exception() 안에 텍스트를 넣는 이유 = SecurityConfig 에서
           System.out.println(exception.getMessage()); 하면 텍스트 출력 가능. */

        // 세션 생성 //
        EntityMemberInfo memberInfo = (EntityMemberInfo) user;
        String userid2 = memberInfo.getUserid();
        String nick = memberInfo.getNick();
        String mail = memberInfo.getMail();

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();

        session.setAttribute("nick", nick);
        session.setAttribute("userid", userid2);
        session.setAttribute("mail", mail);
        // 세션 생성 //

        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        // 위의 Exception을 통과 즉, 회원이면 모두에게 "ROLE_USER" 권한을 준다.

        if(userid.equals("onboard01")){
            authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
        }

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(memberInfo, null, authorities);
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {

        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
