package com.example.Portfolio_Onboard.DTO;

import com.example.Portfolio_Onboard.Entity.EntityPost;
import lombok.Data;

import java.util.List;

@Data
public class DTOSearchBoard {

    private Long bidx;
    private String b_name;
    private List<EntityPost> postList;
}