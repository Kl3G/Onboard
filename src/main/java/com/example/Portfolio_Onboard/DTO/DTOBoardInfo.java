package com.example.Portfolio_Onboard.DTO;

import com.example.Portfolio_Onboard.Repository.RepoWorld;
import lombok.Data;

import java.util.Date;

@Data
public class DTOBoardInfo {

    private Long b_idx;
    private String userid;
    private String nick;
    private String b_name;
    private String intro;
    private Date regdate;
}
