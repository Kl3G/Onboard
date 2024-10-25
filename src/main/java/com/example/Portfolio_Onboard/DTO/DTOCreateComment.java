package com.example.Portfolio_Onboard.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class DTOCreateComment {

    private Long pidx;
    private String userid;
    private String cPwd;
    private String nick;
    private String text;
    private String ip;
    private Date regdate;


}
