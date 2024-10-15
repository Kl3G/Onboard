package com.example.Portfolio_Onboard.Service;

import com.example.Portfolio_Onboard.DTO.DTOJoin;
import com.example.Portfolio_Onboard.Entity.EntityMemberInfo;
import com.example.Portfolio_Onboard.Repository.RepositoryMemberInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ServiceJoinLoginImpl implements ServiceJoinLogin{

    private final RepositoryMemberInfo repositoryMemberInfo;

    @Autowired
    ServiceJoinLoginImpl(RepositoryMemberInfo repositoryMemberInfo){

        this.repositoryMemberInfo = repositoryMemberInfo;
    }


    @Override
    public String setJoin(DTOJoin dtoJoin) {

        repositoryMemberInfo.save(dtoJoin.entityMemberInfo());

        return "redirect:/index";
    }
}
