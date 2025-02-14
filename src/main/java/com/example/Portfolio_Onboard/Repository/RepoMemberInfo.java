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
    Optional<EntityMemberInfo> findByUseridAndPwd(String userid, String pwd);
    // findBy + (컬럼 이름1) + And/Or + (컬럼 이름2)
    // Optional<Entity>: 단일 엔티티가 반환될 것으로 예상될 때.
    // List<Entity>: 여러 개의 엔티티를 반환할 때.

    @Transactional
    @Modifying
    @Query("UPDATE EntityMemberInfo e SET e.pwd = :newPwd WHERE e.mail = :mail AND e.userid = :userid")
    void updatePwdByMailAndUserid(@Param("mail") String mail, @Param("userid") String userid, @Param("newPwd") String newPwd);

    @Transactional
    @Modifying
    @Query("UPDATE EntityMemberInfo e SET e.mail = :newMail WHERE e.userid = :userid")
    void updateMailByUserid(@Param("userid") String userid, @Param("newMail") String newMail);

    @Transactional
    @Modifying
    @Query("UPDATE EntityMemberInfo e SET e.nick = :newNick WHERE e.userid = :userid")
    void updateNickByUserid(@Param("userid") String userid, @Param("newNick") String newNick);
}
