package com.example.Portfolio_Onboard.DTO;

import com.example.Portfolio_Onboard.Entity.EntityMemberInfo;
import com.example.Portfolio_Onboard.Entity.EntityPost;
import com.example.Portfolio_Onboard.Entity.EntityWorld;
import lombok.Data;

import java.util.Date;

@Data
public class DTOCreatePost {

    private Long p_idx;
    private Long b_idx; // 보드 외래키
    private String userid; // 멤버 외래키

    // newdate는 입력 안 함, 수정할 때 입력
    // private Long view_count 입력 안 함
    // private Long good_count 입력 안 함
    private String p_pwd;
    private String nick;
    private String category;
    private String title;
    private String text;
    private String userip;
    private Date regdate;

    public EntityPost entityPost(EntityMemberInfo MemberInfo, EntityWorld board){

        return new EntityPost(null, board, MemberInfo, p_pwd, nick, category, title, text, userip, new Date(), null, null, null);
    }
}
