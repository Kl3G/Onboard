package com.example.Portfolio_Onboard.Repository;
import com.example.Portfolio_Onboard.Entity.EntityMemberInfo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepoMemberInfo extends JpaRepository<EntityMemberInfo, String> {

    EntityMemberInfo findByUserid(String userid);
    EntityMemberInfo findByMail(String mail);
    Optional<EntityMemberInfo> findByMailAndUserid(String mail, String userid);

    @Transactional
    @Modifying
    @Query("UPDATE EntityMemberInfo e SET e.pwd = :newPwd WHERE e.mail = :mail AND e.userid = :userid")
    void updatePwdByMailAndUserid(@Param("mail") String mail, @Param("userid") String userid, @Param("newPwd") String newPwd);
}
