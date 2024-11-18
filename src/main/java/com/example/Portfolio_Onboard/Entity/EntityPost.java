package com.example.Portfolio_Onboard.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "POST")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntityPost {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pidx")
    @SequenceGenerator(name = "pidx", sequenceName = "p_idx", allocationSize = 1)
    private Long pidx;

    @ManyToOne
    @JoinColumn(name = "bidx")
    @ToString.Exclude
    private EntityWorld board; // 보드 외래키
    /* DB에서 레코드를 가져와서 그 레코드의 기본키를 @JoinColumn에 입력한다.
    따라서, DB에 레코드가 반드시 존재해야만 @JoinColumn에 데이터를 입력할 수 있다. */

    @ManyToOne
    @JoinColumn(name = "userid")
    @ToString.Exclude
    private EntityMemberInfo memberInfo; // 멤버 외래키

    private String ppwd;
    private String nick;
    private String category;
    private String title;
    private String text;
    private String userip;
    private Date regdate;
    private Date newdate;
    private Long view_count;
    private Long good_count;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EntityComments> commentList = new ArrayList<>();

    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private EntityFiles files;
}
