package com.example.Portfolio_Onboard.Service;

import com.example.Portfolio_Onboard.Repository.RepoMemberInfo;
import com.example.Portfolio_Onboard.Repository.RepoPost;
import com.example.Portfolio_Onboard.Repository.RepoWorld;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ServiceTestImpl implements ServiceTest{

    private RepoWorld repoWorld;
    private RepoMemberInfo repoMemberInfo;
    private RepoPost repoPost;

    @Autowired
    ServiceTestImpl(RepoMemberInfo repoMemberInfo, RepoWorld repoWorld, RepoPost repoPost){

        this.repoMemberInfo = repoMemberInfo;
        this.repoWorld = repoWorld;
        this.repoPost = repoPost;
    }

    @Override
    public String test() {

        log.error(repoMemberInfo.findById("abcd"));
        //log.error(repoWorld.findById(88L));

        return "test";
    }

    @Override
    public String test2() {

        log.error(repoPost.findAll());

        return "test";
    }
}
