package com.example.Portfolio_Onboard.Service;

import com.example.Portfolio_Onboard.DTO.DTOJoin;
import com.example.Portfolio_Onboard.Repository.RepoMemberInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
public class ServiceJoinImpl implements ServiceJoin {

    private final RepoMemberInfo repoMemberInfo;

    @Autowired
    ServiceJoinImpl(RepoMemberInfo repoMemberInfo){

        this.repoMemberInfo = repoMemberInfo;
    }


    @Override
    public String setJoin(DTOJoin dtoJoin, RedirectAttributes redirectAttributes) {

        repoMemberInfo.save(dtoJoin.entityMemberInfo());
        redirectAttributes.addFlashAttribute("joinSuccess", "회원가입이 완료되었습니다.");

        return "redirect:/index";
    }
}
