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
    @Column(nullable = false)
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

    @Column(nullable = false, length = 4)
    private String ccpwd;

    @Column(nullable = false, length = 10)
    private String nick;

    @Column(nullable = false, length = 500)
    private String text;

    @Column(nullable = false, length = 45)
    private String userip;

    @Column(nullable = false)
    private Date regdate;
}
