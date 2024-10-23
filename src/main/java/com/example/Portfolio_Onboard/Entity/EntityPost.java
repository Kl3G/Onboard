package com.example.Portfolio_Onboard.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "post")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntityPost {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "p_idx")
    @SequenceGenerator(name = "p_idx", sequenceName = "p_idx", allocationSize = 1)
    private Long p_idx;

    @ManyToOne
    @JoinColumn(name = "b_idx")
    @ToString.Exclude
    private EntityWorld board; // 보드 외래키

    @ManyToOne
    @JoinColumn(name = "userid")
    @ToString.Exclude
    private EntityMemberInfo memberInfo; // 멤버 외래키

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
