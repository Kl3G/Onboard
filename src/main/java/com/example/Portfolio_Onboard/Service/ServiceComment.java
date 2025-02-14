package com.example.Portfolio_Onboard.Service;

import com.example.Portfolio_Onboard.DTO.DTOCreateChildComments;
import com.example.Portfolio_Onboard.DTO.DTOCreateComment;
import com.example.Portfolio_Onboard.Entity.EntityComments;

import java.util.List;

public interface ServiceComment {

    String setComment(DTOCreateComment dtoCreateComment);
    String setChildComment(DTOCreateChildComments dtoCreateChildComments);
    long countComments(Long pidx);
    List<EntityComments> getCommentList(Long pidx);
}
