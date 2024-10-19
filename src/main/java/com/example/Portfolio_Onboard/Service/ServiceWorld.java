package com.example.Portfolio_Onboard.Service;

import com.example.Portfolio_Onboard.DTO.DTOBoardView;
import com.example.Portfolio_Onboard.DTO.DTOCreateBoard;

import java.util.List;

public interface ServiceWorld {

    String setWorld(DTOCreateBoard dtoCreateBoard);
    List<DTOBoardView> list();
}
