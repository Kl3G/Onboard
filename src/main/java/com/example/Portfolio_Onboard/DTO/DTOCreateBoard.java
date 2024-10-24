package com.example.Portfolio_Onboard.DTO;

import com.example.Portfolio_Onboard.Entity.EntityMemberInfo;
import com.example.Portfolio_Onboard.Entity.EntityWorld;
import lombok.Data;

import java.util.Date;

@Data
public class DTOCreateBoard {

    private Long bidx;
    private String userid;
    private String nick;
    private String place;
    private String b_name;
    private String intro;
    private String reason;
    private Date regdate;

    public EntityWorld entityWorld(EntityMemberInfo memberInfo){

        return new EntityWorld(null, memberInfo, nick, place, b_name, intro, reason, new Date());
        // 새로운 EntityWorld(entity)객체를 생성하고, 초기화해 준다.
    }
}
