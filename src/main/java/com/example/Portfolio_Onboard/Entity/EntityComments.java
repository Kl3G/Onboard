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
@Table(name = "COMMENTS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntityComments {

    @Id
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

    private String cpwd;
    private String nick;
    private String text;
    private String userip;
    private Date regdate;

    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EntityChildComments> childcommentList = new ArrayList<>();
}
