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
@Data
@Table(name = "world")
@AllArgsConstructor
@NoArgsConstructor
public class EntityWorld {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bidx")
    @SequenceGenerator(name = "bidx", sequenceName = "b_idx", allocationSize = 1)
    private Long bidx;

    @ManyToOne
    @JoinColumn(name = "userid")
    @ToString.Exclude
    private EntityMemberInfo memberInfo;

    @Column(nullable = false, length = 10)
    private String nick;

    @Column(nullable = false, length = 2)
    private String place;

    @Column(nullable = false, length = 30)
    private String b_name;

    @Column(nullable = false, length = 100)
    private String intro;

    @Column(nullable = false, length = 50)
    private String image;

    @Column(nullable = false, length = 100)
    private String reason;

    @Column(nullable = false)
    private Date regdate;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EntityPost> postList = new ArrayList<>();
}
