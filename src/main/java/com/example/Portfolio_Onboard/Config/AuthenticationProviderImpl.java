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

        if (user == null){

            throw new UsernameNotFoundException("없는 아이디..");
        }

        if(!pwd.equals(user.getPassword())){

            throw new BadCredentialsException("비밀번호 오류..");
        }

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

        if(userid.equals("qwer")){
            authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
        }

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(userid, null, authorities);
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {

        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
