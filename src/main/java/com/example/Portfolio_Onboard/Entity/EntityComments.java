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
@Table(name = "comments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntityComments {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cidx")
    @SequenceGenerator(name = "cidx", sequenceName = "cidx", allocationSize = 1)
    private Long cidx;

    @ManyToOne
    @JoinColumn(name = "pidx")
    @ToString.Exclude
    private EntityPost post;

    @ManyToOne
    @JoinColumn(name = "userid")
    @ToString.Exclude
    private EntityMemberInfo memberInfo;

    @Column(nullable = false, length = 4)
    private String cpwd;

    @Column(nullable = false, length = 10)
    private String nick;

    @Column(nullable = false, length = 500)
    private String text;

    @Column(nullable = false, length = 45)
    private String userip;

    @Column(nullable = false)
    private Date regdate;

    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EntityChildComments> childcommentList = new ArrayList<>();
}
