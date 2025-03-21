package com.example.Portfolio_Onboard.DTO;

import com.example.Portfolio_Onboard.Entity.EntityWorld;
import lombok.Data;

@Data
public class DTOPopularPost {

    private Long pidx;
    private Long bidx;
    private String title;
    private String text;
}
