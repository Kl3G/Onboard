package com.example.Portfolio_Onboard.Service;

import com.example.Portfolio_Onboard.DTO.DTOCreatePost;
import com.example.Portfolio_Onboard.Entity.EntityMemberInfo;
import com.example.Portfolio_Onboard.Entity.EntityWorld;
import com.example.Portfolio_Onboard.Repository.RepoMemberInfo;
import com.example.Portfolio_Onboard.Repository.RepoPost;
import com.example.Portfolio_Onboard.Repository.RepoWorld;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class ServiceCreatePostImpl implements ServiceCreatePost{

    private final RepoMemberInfo repoMemberInfo;
    private final RepoWorld repoWorld;
    private final RepoPost repoPost;

    public ServiceCreatePostImpl(RepoMemberInfo repoMemberInfo, RepoWorld repoWorld, RepoPost repoPost) {
        this.repoMemberInfo = repoMemberInfo;
        this.repoWorld = repoWorld;
        this.repoPost = repoPost;
    }

    @Override
    public String setPost(DTOCreatePost dtoCreatePost) {

        EntityMemberInfo MemberInfo = repoMemberInfo.findByUserid(dtoCreatePost.getUserid());
        Optional<EntityWorld> optionalBoard = repoWorld.findById(dtoCreatePost.getBidx());

        log.error("asd");
        log.error(dtoCreatePost.getBidx());

        if (optionalBoard.isPresent()) {

            EntityWorld board = optionalBoard.get();
            repoPost.save(dtoCreatePost.entityPost(MemberInfo, board, repoMemberInfo));
            // board를 사용하여 작업 수행
        }

        return "redirect:/index";
    }
}
