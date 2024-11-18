package com.example.Portfolio_Onboard.Service.Security;

import com.example.Portfolio_Onboard.Repository.RepoMemberInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserDetailsServiceImpl implements UserDetailsService {

    private final RepoMemberInfo repositoryMemberInfo;

    @Override
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {

        return repositoryMemberInfo.findByUserid(userid);
    }
}
