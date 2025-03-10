package com.example.Portfolio_Onboard.Service;

import com.example.Portfolio_Onboard.DTO.DTOModifyMail;
import com.example.Portfolio_Onboard.DTO.DTOModifyNick;
import com.example.Portfolio_Onboard.Repository.RepoMemberInfo;
import org.springframework.stereotype.Service;

@Service
public class ServiceModifyInfoImpl implements ServiceModifyInfo{

    private final RepoMemberInfo repoMemberInfo;

    public ServiceModifyInfoImpl(RepoMemberInfo repoMemberInfo) {

        this.repoMemberInfo = repoMemberInfo;
    }

    @Override
    public String modifyMail(DTOModifyMail dtoModifyMail) {

        repoMemberInfo.updateMailByUserid(dtoModifyMail.getUserid(), dtoModifyMail.getNewMail());

        return "redirect:/index/logout";
    }

    @Override
    public String modifyNick(DTOModifyNick dtoModifyNick) {

        repoMemberInfo.updateNickByUserid(dtoModifyNick.getUserid(), dtoModifyNick.getNewNick());

        return "redirect:/index/logout";
    }

    @Override
    public String withdrawal(String userid, String pwd) {

        repoMemberInfo.deleteByUseridAndPwd(userid, pwd);

        return "redirect:/index/logout";
    }
}
