package com.example.Portfolio_Onboard.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "member_info")
@AllArgsConstructor
@NoArgsConstructor
public class EntityMemberInfo implements UserDetails {

    @Id
    @Column(nullable = false, length = 15)
    private String userid;

    @Column(nullable = false, length = 100)
    private String pwd;

    @Column(nullable = false, length = 10, unique = true)
    private String nick;

    @Column(nullable = false, length = 50, unique = true)
    private String mail;

    @Column(nullable = false)
    private Date regdate;

    @OneToMany(mappedBy = "memberInfo", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true) //기본설정
    private List<EntityWorld> worldList = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of();
    }

    @Override
    public String getUsername() {

        return this.userid;
    }

    @Override
    public String getPassword() {

        return this.pwd;
    }
}
