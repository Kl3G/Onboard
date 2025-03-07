package com.example.Portfolio_Onboard.Repository;

import com.example.Portfolio_Onboard.Entity.EntityPost;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    Page<EntityPost> findByBoard_Bidx(Long bidx, Pageable pageable);

    @Transactional
    @Modifying
    @Query("DELETE FROM EntityPost e WHERE e.userid = :userid AND e.ppwd = :ppwd")
    void deleteByUseridAndPpwd(@Param("userid") String userid, @Param("ppwd") String ppwd);

    @Query("SELECT e FROM EntityPost e WHERE e.title LIKE CONCAT('%', :keyword, '%') OR e.text LIKE CONCAT('%', :keyword, '%')")
    List<EntityPost> findPostByKeyword(@Param("keyword") String keyword, Sort sort);

    @Query("SELECT e FROM EntityPost e WHERE e.title LIKE CONCAT('%', :keyword, '%') OR e.text LIKE CONCAT('%', :keyword, '%')")
    Page<EntityPost> findPostByKeywordPage(@Param("keyword") String keyword, Pageable pageable);
}
