package com.example.Portfolio_Onboard.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "files")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntityFiles {

    @Id
    @Column(nullable = false)
    private Long pidx; // 식별자 필드 추가

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId   // EntityPost의 pidx를 사용하여 EntityFiles의 ID로 설정
    @JoinColumn(name = "pidx")
    @ToString.Exclude
    private EntityPost post;

    @Column(length = 1000)
    private String ofile;

    @Column(length = 1000)
    private String sfile;
}
