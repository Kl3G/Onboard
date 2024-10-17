package com.example.Portfolio_Onboard.Service.Security;

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

        List<GrantedAuthority> authorities = new ArrayList<>();

        if(userid.equals("abcd"))
        {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        if(userid.equals("qwer"))
        {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
        }

        return new UsernamePasswordAuthenticationToken(userid, null, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {

        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
