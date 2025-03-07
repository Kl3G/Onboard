package com.example.Portfolio_Onboard.Service;

import com.example.Portfolio_Onboard.DTO.DTOSearchBoard;
import com.example.Portfolio_Onboard.DTO.DTOSearchPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ServiceSearch {

    List<DTOSearchBoard> setSearchResultBoard(String keyword);
    List<DTOSearchPost> setSearchResultPost(String keyword, Sort sort);

    Page<DTOSearchBoard> worldList(String keyword, Pageable pageable);
    Page<DTOSearchPost> postList(String keyword, Pageable pageable);
}
