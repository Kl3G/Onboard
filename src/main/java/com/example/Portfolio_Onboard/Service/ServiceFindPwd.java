package com.example.Portfolio_Onboard.Service;

import com.example.Portfolio_Onboard.DTO.DTOCheckPwdResult;
import com.example.Portfolio_Onboard.DTO.DTOFindPwd;
import com.example.Portfolio_Onboard.DTO.DTOModifyPwdCheck;
import com.example.Portfolio_Onboard.DTO.DTONewPwd;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface ServiceFindPwd {

    String findPwd(DTOFindPwd dtoFindPwd, RedirectAttributes redirectAttributes);
    String newPwd(DTONewPwd dtoNewPwd);
    DTOCheckPwdResult checkPwd(DTOModifyPwdCheck dtoModifyPwdCheck);
}
