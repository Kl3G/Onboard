package com.example.Portfolio_Onboard.Service;

import com.example.Portfolio_Onboard.DTO.DTOJoin;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

public interface ServiceJoin {

    String setJoin(DTOJoin dtoJoin, RedirectAttributes redirectAttributes);
    Map<String, Boolean> duplicateUserid (String userid);
    Map<String, Boolean> duplicateNick (String nick);
}
