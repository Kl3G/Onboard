package com.example.Portfolio_Onboard.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "post")
@Data
public class EntityPost {

    @Id
    private Long p_idx;
    private Long b_idx; // 보드 외래키
    private String userid; // 멤버 외래키

    private String p_pwd;
    private String nick;
    private String category;
    private String title;
    private String text;
    private String userip;
    private Date regdate;
    private Date newdate;
    private Long view_count;
    private Long good_count;
}
