package com.example.Portfolio_Onboard.Repository;

import com.example.Portfolio_Onboard.DTO.DTOBoardView;
import com.example.Portfolio_Onboard.Entity.EntityWorld;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RepoWorld extends JpaRepository<EntityWorld, Long> {

    Optional<EntityWorld> findById(Long b_idx);
    @Query("SELECT new com.example.Portfolio_Onboard.DTO.DTOBoardView(w.b_idx, w.nick, w.place, w.b_name) " +
            "FROM EntityWorld w WHERE w.place = :place")
    List<DTOBoardView> findByPlace(String place);
}
