package com.example.Portfolio_Onboard.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "FILES")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntityFiles {

    @Id
    private Long pidx; // 식별자 필드 추가

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId   // EntityPost의 pidx를 사용하여 EntityFiles의 ID로 설정
    @JoinColumn(name = "pidx")
    @ToString.Exclude
    private EntityPost post;

    private String ofile;
    private String sfile;
}
