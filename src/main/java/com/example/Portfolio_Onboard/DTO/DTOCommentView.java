package com.example.Portfolio_Onboard.DTO;

import com.example.Portfolio_Onboard.Entity.EntityChildComments;
import com.example.Portfolio_Onboard.Entity.EntityPost;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DTOCommentView {

    private Long cidx;
    private EntityPost post;
    private String nick;
    private String text;
    private String userip;
    private Date regdate;
    private List<EntityChildComments> childcommentList;
}
