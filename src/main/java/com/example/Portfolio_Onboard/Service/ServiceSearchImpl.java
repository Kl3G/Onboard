package com.example.Portfolio_Onboard.Service;

import com.example.Portfolio_Onboard.Entity.EntityPost;
import com.example.Portfolio_Onboard.Entity.EntityWorld;
import com.example.Portfolio_Onboard.Repository.RepoPost;
import com.example.Portfolio_Onboard.Repository.RepoWorld;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Service
public class ServiceSearchImpl implements ServiceSearch{

    final private RepoWorld repoWorld;
    final private RepoPost repoPost;

    public ServiceSearchImpl(RepoWorld repoWorld, RepoPost repoPost) {

        this.repoWorld = repoWorld;
        this.repoPost = repoPost;
    }

    @Override
    public List<EntityWorld> setSearchResultBoard(String keyword) {

        return repoWorld.findBoardByKeyword(keyword);
    }

    @Override
    public List<EntityPost> setSearchResultPost(String keyword) {

        return repoPost.findPostByKeyword(keyword);
    }


}
