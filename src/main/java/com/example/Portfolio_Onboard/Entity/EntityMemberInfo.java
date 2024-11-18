package com.example.Portfolio_Onboard.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private String userid;
    private String pwd;
    private String nick;
    private String mail;
    private Date regdate;

    @OneToMany(mappedBy = "memberInfo", fetch = FetchType.LAZY) //기본설정
    private List<EntityWorld> worldList = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return this.pwd;
    }

    @Override
    public String getUsername() {
        return this.userid;
    }
}
