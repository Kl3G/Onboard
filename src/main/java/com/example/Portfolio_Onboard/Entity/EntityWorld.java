package com.example.Portfolio_Onboard.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Table(name = "world")
@AllArgsConstructor
@NoArgsConstructor
public class EntityWorld {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "b_idx")
    @SequenceGenerator(name = "b_idx", sequenceName = "b_idx", allocationSize = 1)
    private Long b_idx;

    private String userid;
    private String nick;
    private String place;
    private String b_name;
    private String intro;
    private String reason;
    private Date regdate;
}
