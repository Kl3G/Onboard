package com.example.Portfolio_Onboard.Service;

import com.example.Portfolio_Onboard.DTO.DTOCommentView;
import com.example.Portfolio_Onboard.DTO.DTOCreateChildComments;
import com.example.Portfolio_Onboard.DTO.DTOCreateComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ServiceComment {

    String setComment(DTOCreateComment dtoCreateComment);
    String setChildComment(DTOCreateChildComments dtoCreateChildComments);
    long countComments(Long pidx);
    Page<DTOCommentView> getCommentList(Long pidx, Pageable pageable);
}
