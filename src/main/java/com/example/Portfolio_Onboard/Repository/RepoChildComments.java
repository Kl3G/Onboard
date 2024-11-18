package com.example.Portfolio_Onboard.Repository;

import com.example.Portfolio_Onboard.Entity.EntityChildComments;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RepoChildComments extends JpaRepository<EntityChildComments, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM EntityChildComments e WHERE e.ccidx = :ccidx AND e.ccpwd = :ccpwd")
    void deleteByCcidxAndCcpwd(@Param("ccidx") Long ccidx, @Param("ccpwd") String ccpwd);
}
