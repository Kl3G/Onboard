package com.example.Portfolio_Onboard.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "files")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntityFiles {

    @Id
    private Long pidx; // 식별자 필드 추가

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId   // EntityPost의 pidx를 사용하여 EntityFiles의 ID로 설정
    @JoinColumn(name = "pidx")
    private EntityPost post;

    private String ofile;
}
