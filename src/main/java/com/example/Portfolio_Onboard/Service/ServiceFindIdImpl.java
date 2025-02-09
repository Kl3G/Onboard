package com.example.Portfolio_Onboard.Service;

import com.example.Portfolio_Onboard.DTO.DTOFindId;
import com.example.Portfolio_Onboard.Entity.EntityMemberInfo;
import com.example.Portfolio_Onboard.Repository.RepoMemberInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
@Log4j2
public class ServiceFindIdImpl implements ServiceFindId{

    private final RepoMemberInfo repoMemberInfo;

    public ServiceFindIdImpl(RepoMemberInfo repoMemberInfo) {

        this.repoMemberInfo = repoMemberInfo;
    }

    @Override
    public String findId(DTOFindId dtoFindId, RedirectAttributes redirectAttributes) {

        EntityMemberInfo memberInfo = repoMemberInfo.findByMail(dtoFindId.getMail());

        if(memberInfo == null){

            return "redirect:/idNotFound";
        }

        String userid = memberInfo.getUserid();

        redirectAttributes.addFlashAttribute("userid", userid);

        return "redirect:/foundInfo";
    }
}
