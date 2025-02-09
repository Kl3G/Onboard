package com.example.Portfolio_Onboard.Service;

import com.example.Portfolio_Onboard.DTO.DTOFindPwd;
import com.example.Portfolio_Onboard.DTO.DTONewPwd;
import com.example.Portfolio_Onboard.Entity.EntityMemberInfo;
import com.example.Portfolio_Onboard.Repository.RepoMemberInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Service
public class ServiceFindPwdImpl implements ServiceFindPwd{

    private final RepoMemberInfo repoMemberInfo;

    public ServiceFindPwdImpl(RepoMemberInfo repoMemberInfo) {

        this.repoMemberInfo = repoMemberInfo;
    }

    @Override
    public String findPwd(DTOFindPwd dtoFindPwd, RedirectAttributes redirectAttributes) {

        Optional<EntityMemberInfo> entityMemberInfo = repoMemberInfo.findByMailAndUserid(dtoFindPwd.getMail(), dtoFindPwd.getUserid());

        if(entityMemberInfo.isPresent()){

            EntityMemberInfo memberInfo = entityMemberInfo.get();
            redirectAttributes.addFlashAttribute("memberInfo", memberInfo);

            return "redirect:/newPwd";
        }else{

            return "redirect:/pwdNotFound";
        }
    }

    @Override
    public String newPwd(DTONewPwd dtoNewPwd) {

        repoMemberInfo.updatePwdByMailAndUserid(dtoNewPwd.getMail(), dtoNewPwd.getUserid(), dtoNewPwd.getNewPwd());

        return "redirect:/index";
    }
}
