package com.example.Portfolio_Onboard.DTO;

import com.example.Portfolio_Onboard.Entity.EntityChildComments;
import com.example.Portfolio_Onboard.Entity.EntityComments;
import com.example.Portfolio_Onboard.Entity.EntityMemberInfo;
import lombok.Data;

import java.util.Date;

@Data
public class DTOCreateChildComments {

    private Long cidx;
    private String userid;
    private String ccpwd;
    private String nick;
    private String text;
    private String userip;
    private Date regdate;

    public EntityChildComments setEntityChildComments(EntityComments comment, EntityMemberInfo memberInfo) {

        return new EntityChildComments(null, comment, memberInfo, ccpwd, nick, text, userip, new Date());
    }
}
