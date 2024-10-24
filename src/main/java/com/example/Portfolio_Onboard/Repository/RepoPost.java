package com.example.Portfolio_Onboard.Repository;

import com.example.Portfolio_Onboard.Entity.EntityPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepoPost extends JpaRepository<EntityPost, Long> {

    List<EntityPost> findByBoard_Bidx(Long bidx);
}
