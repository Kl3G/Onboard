package com.example.Portfolio_Onboard.Service;

import com.example.Portfolio_Onboard.DTO.DTOBoardInfo;
import com.example.Portfolio_Onboard.DTO.DTOBoardView;
import com.example.Portfolio_Onboard.DTO.DTOCreateBoard;
import com.example.Portfolio_Onboard.DTO.DTOPostView;
import com.example.Portfolio_Onboard.Entity.EntityPost;

import java.util.List;
import java.util.Optional;

public interface ServiceWorld {

    String setWorld(DTOCreateBoard dtoCreateBoard);
    List<DTOBoardView> list();
    List<DTOBoardView> list2(String place);
    DTOBoardInfo boardInfo(Long bidx);
    List<DTOPostView> postList(Long bidx); // 보드의 게시글 리스트 출력
    void incrementViewCount(Long pidx); // 조회수 카운트
    List<EntityPost> countPost(); //전체게시물 카운트
    EntityPost postView(Long pidx);
}
