package com.example.Portfolio_Onboard.Repository;

import com.example.Portfolio_Onboard.Entity.EntityComments;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoComment extends JpaRepository<EntityComments, Long> {

    long countByPost_Pidx(Long pidx);
    List<EntityComments> findByPost_Pidx(Long pidx);
    EntityComments findByCidx(Long cidx);

    @Transactional
    @Modifying
    @Query("DELETE FROM EntityComments e WHERE e.cidx = :cidx AND e.cpwd = :cpwd")
    void deleteByCidxAndCpwd(@Param("cidx") Long cidx, @Param("cpwd") String cpwd);
}
