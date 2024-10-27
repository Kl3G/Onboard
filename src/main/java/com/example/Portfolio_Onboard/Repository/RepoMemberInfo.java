package com.example.Portfolio_Onboard.Repository;

import com.example.Portfolio_Onboard.Entity.EntityMemberInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoMemberInfo extends JpaRepository<EntityMemberInfo, String> {

    EntityMemberInfo findByUserid(String userid);
}
