package com.example.Portfolio_Onboard.Repository;

import com.example.Portfolio_Onboard.DTO.DTOBoardView;
import com.example.Portfolio_Onboard.Entity.EntityMemberInfo;
import com.example.Portfolio_Onboard.Entity.EntityWorld;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RepoWorld extends JpaRepository<EntityWorld, Long> {

    Optional<EntityWorld> findById(Long b_idx);

    @Query("SELECT w FROM EntityWorld w WHERE w.place = :place")
    /*@Query("SELECT new com.example.Portfolio_Onboard.DTO.DTOBoardView(w.b_idx, w.nick, w.place, w.b_name) " +
            "FROM EntityWorld w WHERE w.place = :place")*/
    /*@Query("SELECT new com.example.DTOBoardView(w.b_idx, w.b_name) FROM EntityWorld w WHERE w.place = :place")
    List<DTOBoardView> findByPlace(@Param("place") String place);*/
    List<EntityWorld> findByPlace(@Param("place") String place);

/*
    findBy 뒤에 나오는 Property는 반드시 엔티티 클래스의 필드 이름과 정확히 일치해야 합니다, 대소문자도 구분되므로 주의해야 합니다.
    [Property]는 리포지토리 메서드에서 사용하는 속성을 나타내는 자리 표시자입니다.
    즉, 실제 데이터베이스 엔티티 클래스에서 사용하는 필드 이름을 기준으로 메서드 이름을 정의해야 합니다.
*/
}
