package com.example.Portfolio_Onboard.DTO;

import com.example.Portfolio_Onboard.Entity.EntityMemberInfo;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
public class DTOJoin {

    private String userid;
    private String pwd;
    private String nick;
    private String mail;
    private Date regdate;

    public EntityMemberInfo entityMemberInfo(){

        return new EntityMemberInfo(userid, pwd, nick, mail, new Date(), null);
    }
}
