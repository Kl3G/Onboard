package com.example.Portfolio_Onboard.Service;

import com.example.Portfolio_Onboard.DTO.DTOCreatePost;
import com.example.Portfolio_Onboard.DTO.DTOModifyPost;
import com.example.Portfolio_Onboard.Entity.EntityPost;

import java.util.Optional;

public interface ServiceCreatePost {

    String setPost(DTOCreatePost dtoCreatePost); // 게시글 작성
    String updatePost(DTOModifyPost dtoModifyPost); // 게시글 수정
    Optional<EntityPost> createOrUpdate(Long pidx);
}
