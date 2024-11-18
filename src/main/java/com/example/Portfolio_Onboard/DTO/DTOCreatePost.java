package com.example.Portfolio_Onboard.DTO;

import com.example.Portfolio_Onboard.Entity.EntityFiles;
import com.example.Portfolio_Onboard.Entity.EntityMemberInfo;
import com.example.Portfolio_Onboard.Entity.EntityPost;
import com.example.Portfolio_Onboard.Entity.EntityWorld;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import java.util.Date;

@Data
public class DTOCreatePost {

    private Long pidx;
    private Long bidx; // 보드 외래키
    private String userid; // 멤버 외래키

    // newdate는 입력 안 함, 수정할 때 입력
    // private Long view_count 입력 안 함
    // private Long good_count 입력 안 함
    private String ppwd;
    private String nick;
    private String category;
    private String title;
    private String text;
    private String userip;
    private MultipartFile[] files;

    public EntityPost entityPost(EntityMemberInfo memberInfo, EntityWorld board, EntityFiles entityFiles){


        return new EntityPost(null, board, memberInfo, ppwd, nick, category, title, text, userip, new Date(), null, 0L, 0L, null, entityFiles);
    }
}
