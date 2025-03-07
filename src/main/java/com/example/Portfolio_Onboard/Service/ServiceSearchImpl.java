package com.example.Portfolio_Onboard.Service;

import com.example.Portfolio_Onboard.DTO.DTOSearchBoard;
import com.example.Portfolio_Onboard.DTO.DTOSearchPost;
import com.example.Portfolio_Onboard.Entity.EntityPost;
import com.example.Portfolio_Onboard.Entity.EntityWorld;
import com.example.Portfolio_Onboard.Repository.RepoPost;
import com.example.Portfolio_Onboard.Repository.RepoWorld;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceSearchImpl implements ServiceSearch{

    final private RepoWorld repoWorld;
    final private RepoPost repoPost;

    public ServiceSearchImpl(RepoWorld repoWorld, RepoPost repoPost) {

        this.repoWorld = repoWorld;
        this.repoPost = repoPost;
    }

    @Override
    public List<DTOSearchBoard> setSearchResultBoard(String keyword) {

        List<EntityWorld> boardList = repoWorld.findBoardByKeyword(keyword);

        List<DTOSearchBoard> dtoSearchBoards = boardList.stream()
                .map(entityBoard -> {

                    DTOSearchBoard dto = new DTOSearchBoard();
                    dto.setBidx(entityBoard.getBidx());
                    dto.setB_name(entityBoard.getB_name());
                    dto.setPostList(entityBoard.getPostList());
                    return dto;
                }).collect(Collectors.toList());

        return dtoSearchBoards;
    }

    @Override
    public List<DTOSearchPost> setSearchResultPost(String keyword, Sort sort) {

        List<EntityPost> postList = repoPost.findPostByKeyword(keyword, sort);

        List<DTOSearchPost> dtoSearchPosts = postList.stream()
            .map(entityPost -> {

                DTOSearchPost dto = new DTOSearchPost();
                dto.setPidx(entityPost.getPidx());
                dto.setBoard(entityPost.getBoard());
                dto.setTitle(entityPost.getTitle());
                dto.setText(entityPost.getText());
                dto.setRegdate(entityPost.getRegdate());
                return dto;
            })
            .collect(Collectors.toList());

        return dtoSearchPosts;
    }

    @Override
    public Page<DTOSearchBoard> worldList(String keyword, Pageable pageable) {

        Page<EntityWorld> boardList = repoWorld.findBoardByKeywordPage(keyword, pageable);

        Page<DTOSearchBoard> boardPage = boardList
                .map(entityBoard -> {

                    DTOSearchBoard dto = new DTOSearchBoard();
                    dto.setBidx(entityBoard.getBidx());
                    dto.setB_name(entityBoard.getB_name());
                    dto.setPostList(entityBoard.getPostList());
                    return dto;
                });

        return boardPage;
    }

    @Override
    public Page<DTOSearchPost> postList(String keyword, Pageable pageable) {

        Page<EntityPost> postList = repoPost.findPostByKeywordPage(keyword, pageable);

        Page<DTOSearchPost> postPage = postList
                .map(entityPost -> {

                    DTOSearchPost dto = new DTOSearchPost();
                    dto.setPidx(entityPost.getPidx());
                    dto.setText(entityPost.getText());
                    dto.setTitle(entityPost.getTitle());
                    dto.setRegdate(entityPost.getRegdate());
                    dto.setBoard(entityPost.getBoard());
                    dto.setViewCount(entityPost.getViewCount());
                    dto.setGoodCount(entityPost.getGoodCount());

                    return dto;
                });

        return postPage;
    }


}
