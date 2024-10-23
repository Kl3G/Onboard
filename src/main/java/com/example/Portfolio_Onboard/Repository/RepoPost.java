package com.example.Portfolio_Onboard.Repository;

import com.example.Portfolio_Onboard.Entity.EntityPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoPost extends JpaRepository<EntityPost, Long> {

}
