package com.example.Portfolio_Onboard.Service;

import com.example.Portfolio_Onboard.DTO.DTOJoin;
import com.example.Portfolio_Onboard.Repository.RepoMemberInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceJoinImpl implements ServiceJoin {

    private final RepoMemberInfo repoMemberInfo;

    @Autowired
    ServiceJoinImpl(RepoMemberInfo repoMemberInfo){

        this.repoMemberInfo = repoMemberInfo;
    }


    @Override
    public String setJoin(DTOJoin dtoJoin) {

        repoMemberInfo.save(dtoJoin.entityMemberInfo());

        return "redirect:/index";
    }
}
