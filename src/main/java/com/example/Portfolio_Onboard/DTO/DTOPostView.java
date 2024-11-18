package com.example.Portfolio_Onboard.DTO;

import com.example.Portfolio_Onboard.Repository.RepoPost;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DTOPostView {

    private Long pidx;
    private Long bidx;
    private String nick;
    private String userip;
    private String category;
    private String title;
    private Date regdate;
    private Long view_count;
    private Long good_count;
}
