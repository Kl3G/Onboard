package com.example.Portfolio_Onboard.Service;

import com.example.Portfolio_Onboard.DTO.DTOJoin;
import com.example.Portfolio_Onboard.Entity.EntityMemberInfo;
import com.example.Portfolio_Onboard.Repository.RepoMemberInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ServiceJoinImpl implements ServiceJoin {

    private final RepoMemberInfo repoMemberInfo;

    @Autowired
    ServiceJoinImpl(RepoMemberInfo repoMemberInfo){

        this.repoMemberInfo = repoMemberInfo;
    }


    @Override
    public String setJoin(DTOJoin dtoJoin, RedirectAttributes redirectAttributes) {

        if (repoMemberInfo.existsById(dtoJoin.getUserid()) ||
                repoMemberInfo.existsByNick(dtoJoin.getNick()) ||
                repoMemberInfo.existsByMail(dtoJoin.getMail())) {

            redirectAttributes.addFlashAttribute("joinDuplicate", "joinDuplicate");
            return "redirect:/index";
        }

        repoMemberInfo.save(dtoJoin.entityMemberInfo());
        redirectAttributes.addFlashAttribute("joinSuccess", "회원가입이 완료되었습니다.");

        return "redirect:/index";
    }

    @Override
    public Map<String, Boolean> duplicateUserid(String userid) {

        EntityMemberInfo entityMemberInfo = repoMemberInfo.findByUserid(userid);

        Map<String, Boolean> response = new HashMap<>();

        boolean exists = false;

        if (entityMemberInfo == null) {

            response.put("exists", exists);
        } else {

            exists = true;

            response.put("exists", exists);
        }

        return response;
    }

    @Override
    public Map<String, Boolean> duplicateNick(String nick) {

        EntityMemberInfo entityMemberInfo = repoMemberInfo.findByNick(nick);

        Map<String, Boolean> response = new HashMap<>();

        boolean exists = false;

        if (entityMemberInfo == null) {

            response.put("exists", exists);
        } else {

            exists = true;

            response.put("exists", exists);
        }

        return response;
    }
}
