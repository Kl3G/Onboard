package com.example.Portfolio_Onboard.Service;

import com.example.Portfolio_Onboard.DTO.DTOModifyMail;
import com.example.Portfolio_Onboard.DTO.DTOModifyNick;

public interface ServiceModifyInfo {

    String modifyMail(DTOModifyMail dtoModifyMail);
    String modifyNick(DTOModifyNick dtoModifyNick);
}
