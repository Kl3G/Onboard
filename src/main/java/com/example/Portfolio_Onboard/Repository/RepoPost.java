package com.example.Portfolio_Onboard.Repository;

import com.example.Portfolio_Onboard.Entity.EntityPost;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoPost extends JpaRepository<EntityPost, Long> {

    List<EntityPost> findByBoard_Bidx(Long bidx);
    EntityPost findByPidx(Long pidx);

    @Transactional
    @Modifying
    @Query("DELETE FROM EntityPost e WHERE e.memberInfo.userid = :userid AND e.ppwd = :ppwd")
    void deleteByUseridAndPpwd(@Param("userid") String userid, @Param("ppwd") String ppwd);
}
