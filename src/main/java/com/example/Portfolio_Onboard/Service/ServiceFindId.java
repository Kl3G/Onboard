package com.example.Portfolio_Onboard.Service;

import com.example.Portfolio_Onboard.DTO.DTOFindId;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface ServiceFindId {

    String findId(DTOFindId dtoFindId, RedirectAttributes redirectAttributes);
}
