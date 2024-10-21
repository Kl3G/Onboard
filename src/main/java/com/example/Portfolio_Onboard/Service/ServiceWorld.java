package com.example.Portfolio_Onboard.Service;

import com.example.Portfolio_Onboard.DTO.DTOBoardInfo;
import com.example.Portfolio_Onboard.DTO.DTOBoardView;
import com.example.Portfolio_Onboard.DTO.DTOCreateBoard;

import java.util.List;
import java.util.Optional;

public interface ServiceWorld {

    String setWorld(DTOCreateBoard dtoCreateBoard);
    List<DTOBoardView> list();
    List<DTOBoardView> list2(String place);
    DTOBoardInfo boardInfo(Long b_idx);
}
