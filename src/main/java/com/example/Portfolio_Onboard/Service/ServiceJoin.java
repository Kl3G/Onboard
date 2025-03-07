package com.example.Portfolio_Onboard.Service;

import com.example.Portfolio_Onboard.DTO.DTOJoin;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface ServiceJoin {

    String setJoin(DTOJoin dtoJoin, RedirectAttributes redirectAttributes);
}
