package com.example.Portfolio_Onboard.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "comments")
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

    private String cPwd;
    private String nick;
    private String text;
    private String ip;
    private Date regdate;
}
