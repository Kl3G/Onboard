package com.example.Portfolio_Onboard.DTO;

import com.example.Portfolio_Onboard.Entity.EntityMemberInfo;
import com.example.Portfolio_Onboard.Entity.EntityWorld;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@Data
public class DTOCreateBoard {

    private Long bidx;
    private String userid;
    private String nick;
    private String place;
    private String b_name;
    private MultipartFile files;
    private String intro;
    private String reason;
    private Date regdate;

    public EntityWorld entityWorld(EntityMemberInfo memberInfo, String file){

        return new EntityWorld(null, memberInfo, nick, place, b_name, intro, file, reason, new Date(), null);
        // 새로운 EntityWorld(entity)객체를 생성하고, 초기화해 준다.
    }
}
