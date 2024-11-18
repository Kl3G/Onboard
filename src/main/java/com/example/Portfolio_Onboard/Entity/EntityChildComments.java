package com.example.Portfolio_Onboard.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "childcomments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntityChildComments {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ccidx")
    @SequenceGenerator(name = "ccidx", sequenceName = "ccidx", allocationSize = 1)
    private Long ccidx;

    @ManyToOne
    @JoinColumn(name = "cidx")
    @ToString.Exclude
    private EntityComments comment;

    @ManyToOne
    @JoinColumn(name = "userid")
    @ToString.Exclude
    private EntityMemberInfo memberInfo;

    private String ccpwd;
    private String nick;
    private String text;
    private String userip;
    private Date regdate;
}
