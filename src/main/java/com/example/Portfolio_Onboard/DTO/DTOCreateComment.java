package com.example.Portfolio_Onboard.DTO;

import com.example.Portfolio_Onboard.Entity.EntityComments;
import com.example.Portfolio_Onboard.Entity.EntityMemberInfo;
import com.example.Portfolio_Onboard.Entity.EntityPost;
import com.example.Portfolio_Onboard.Repository.RepoMemberInfo;
import lombok.Data;

import java.util.Date;

@Data
public class DTOCreateComment {

    private Long pidx;
    private String userid;
    private String cpwd;
    private String nick;
    private String text;
    private String userip;
    private Date regdate;

    public EntityComments entityComment(EntityPost post, EntityMemberInfo memberInfo){

        return new EntityComments(null, post, memberInfo, cpwd, nick, text, userip, new Date(), null);
    }
}
