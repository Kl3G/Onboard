package com.example.Portfolio_Onboard.DTO;

import com.example.Portfolio_Onboard.Entity.EntityWorld;
import lombok.Data;

import java.util.Date;

@Data
public class DTOSearchPost {

    private Long pidx;
    private EntityWorld board;
    private String title;
    private String text;
    private Date regdate;
    private Long viewCount;
    private Long goodCount;
}
