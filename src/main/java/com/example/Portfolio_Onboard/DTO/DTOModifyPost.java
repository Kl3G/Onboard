package com.example.Portfolio_Onboard.DTO;

import lombok.Data;

@Data
public class DTOModifyPost {

    private Long pidx;
    private String ppwd;
    private String category;
    private String title;
    private String text;
    private String userip;
}
