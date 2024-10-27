package com.example.Portfolio_Onboard.Repository;

import com.example.Portfolio_Onboard.Entity.EntityComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoComment extends JpaRepository<EntityComments, Long> {

    long countByPost_Pidx(Long pidx);
    List<EntityComments> findByPost_Pidx(Long pidx);
}
