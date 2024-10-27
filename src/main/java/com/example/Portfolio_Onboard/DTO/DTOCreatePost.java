package com.example.Portfolio_Onboard.DTO;

import com.example.Portfolio_Onboard.Entity.EntityMemberInfo;
import com.example.Portfolio_Onboard.Entity.EntityPost;
import com.example.Portfolio_Onboard.Entity.EntityWorld;
import com.example.Portfolio_Onboard.Repository.RepoMemberInfo;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class DTOCreatePost {

    private Long pidx;
    private Long bidx; // 보드 외래키
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

    public EntityPost entityPost(EntityMemberInfo memberInfo, EntityWorld board/*RepoMemberInfo repoMemberInfo*/){

/*        if(memberInfo == null){

            EntityMemberInfo guest = repoMemberInfo.findByUserid("guest");
            *//* String guestUserId = "guest_" + UUID.randomUUID().toString();
            EntityMemberInfo guestUser  = new EntityMemberInfo(guestUserId, "ㅇㅇ", "ㅇㅇ", "ㅇㅇ", new Date(), null);
            repoMemberInfo.save(guestUser); *//*
            // 게시글을 적을 때마다 MemberInfo 테이블에 레코드가 생성돼서 사용 안 함

            return new EntityPost(null, board, guest , p_pwd, nick, category, title, text, userip, new Date(), null, 0L, 0L);
        }else {*/

        return new EntityPost(null, board, memberInfo, p_pwd, nick, category, title, text, userip, new Date(), null, 0L, 0L);
    }
}
