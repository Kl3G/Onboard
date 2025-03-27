package com.example.Portfolio_Onboard.Service;

import com.example.Portfolio_Onboard.DTO.*;
import com.example.Portfolio_Onboard.Entity.EntityPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ServiceWorld {

    String setWorld(DTOCreateBoard dtoCreateBoard);
    DTOBoardInfo boardInfo(Long bidx);
    Page<DTOPostView> postList(Long bidx, Pageable pageable); // 보드의 게시글 리스트 출력
    void incrementViewCount(Long pidx); // 조회수 카운트
    void incrementGoodCount(Long pidx);
    List<DTOBoardView> list(); // 보드리스트 출력
    int countBoard(); // 전체보드 카운트
    List<DTOBoardView> list2(String place); // 대륙보드 카운트, 대륙보드 출력
    int countPost(); // 전체게시글 카운트
    int countComments(); // 전체댓글 카운트
    EntityPost postView(Long pidx); // 게시글 데이터 전송
    List<DTOPopularPost> getPopularPost(); // 인기 게시글 전송
    List<DTOPopularPost> getPopularPostOfPlace(String boardPlace); // 대륙별 인기 게시글 전송
    Map<String, Boolean> duplicateBoardName(String boardName);
}
