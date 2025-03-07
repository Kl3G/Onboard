package com.example.Portfolio_Onboard.Repository;

import com.example.Portfolio_Onboard.Entity.EntityComments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoComment extends JpaRepository<EntityComments, Long> {

    long countByPost_Pidx(Long pidx);
    Page<EntityComments> findByPost_Pidx(Long pidx, Pageable pageable);
    EntityComments findByCidx(Long cidx);
}
