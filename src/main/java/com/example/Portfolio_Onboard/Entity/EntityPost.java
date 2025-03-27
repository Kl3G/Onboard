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
@Table(name = "post")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntityPost {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pidx")
    @SequenceGenerator(name = "pidx", sequenceName = "p_idx", allocationSize = 1)
    private Long pidx;

    @ManyToOne
    @JoinColumn(name = "bidx")
    @ToString.Exclude
    private EntityWorld board; // 보드 외래키
    /* DB에서 레코드를 가져와서 그 레코드의 기본키를 @JoinColumn에 입력한다.
    따라서, DB에 레코드가 반드시 존재해야만 @JoinColumn에 데이터를 입력할 수 있다. */

    @Column(nullable = false, length = 15)
    private String userid; // 멤버 외래키

    @Column(nullable = false, length = 4)
    private String ppwd;

    @Column(nullable = false, length = 10)
    private String nick;

    @Column(nullable = false, length = 10)
    private String category;

    @Column(nullable = false, length = 25)
    private String title;

    @Column(nullable = false, length = 3000)
    private String text;

    @Column(nullable = false, length = 45)
    private String userip;

    @Column(nullable = false)
    private Date regdate;

    @Column(nullable = true)
    private Date newdate;

    @Column(nullable = true)
    private Long viewCount;

    @Column(nullable = true)
    private Long goodCount;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EntityComments> commentList = new ArrayList<>();

    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private EntityFiles files;
}
