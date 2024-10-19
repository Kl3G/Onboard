package com.example.Portfolio_Onboard.DTO;

import com.example.Portfolio_Onboard.Entity.EntityWorld;
import lombok.Data;

import java.util.Date;

@Data
public class DTOCreateBoard {

    private Long b_idx;
    private String userid;
    private String nick;
    private String place;
    private String b_name;
    private String intro;
    private String reason;
    private Date regdate;

    public EntityWorld entityWorld(){

        return new EntityWorld(null, userid, nick, place, b_name, intro, reason, new Date());
    }
}
