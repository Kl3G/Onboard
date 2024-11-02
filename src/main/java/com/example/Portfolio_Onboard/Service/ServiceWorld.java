package com.example.Portfolio_Onboard.Service;

import com.example.Portfolio_Onboard.DTO.DTOBoardInfo;
import com.example.Portfolio_Onboard.DTO.DTOBoardView;
import com.example.Portfolio_Onboard.DTO.DTOCreateBoard;
import com.example.Portfolio_Onboard.DTO.DTOPostView;
import com.example.Portfolio_Onboard.Entity.EntityComments;
import com.example.Portfolio_Onboard.Entity.EntityPost;

import java.util.List;
import java.util.Optional;

public interface ServiceWorld {

    String setWorld(DTOCreateBoard dtoCreateBoard);
    DTOBoardInfo boardInfo(Long bidx);
    List<DTOPostView> postList(Long bidx); // 보드의 게시글 리스트 출력
    void incrementViewCount(Long pidx); // 조회수 카운트
    List<DTOBoardView> list(); // 보드리스트 출력
    int countBoard(); // 전체보드 카운트
    List<DTOBoardView> list2(String place); // 대륙보드 카운트, 대륙보드 출력
    int countPost(); // 전체게시글 카운트
    int countComments(); // 전체댓글 카운트
    EntityPost postView(Long pidx);
}
