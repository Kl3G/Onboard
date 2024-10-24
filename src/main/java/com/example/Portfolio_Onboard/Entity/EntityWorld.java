package com.example.Portfolio_Onboard.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Entity
@Data
@Table(name = "world")
@AllArgsConstructor
@NoArgsConstructor
public class EntityWorld {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bidx")
    @SequenceGenerator(name = "bidx", sequenceName = "b_idx", allocationSize = 1)
    private Long bidx;

    @ManyToOne
    @JoinColumn(name = "userid")
    @ToString.Exclude
    private EntityMemberInfo memberInfo;

    private String nick;
    private String place;
    private String b_name;
    private String intro;
    private String reason;
    private Date regdate;
}
