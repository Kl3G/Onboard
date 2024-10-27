package com.example.Portfolio_Onboard.Repository;

import com.example.Portfolio_Onboard.Entity.EntityPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoPost extends JpaRepository<EntityPost, Long> {

    List<EntityPost> findByBoard_Bidx(Long bidx);
    EntityPost findByPidx(Long pidx);
}
