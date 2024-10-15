package com.example.Portfolio_Onboard.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name="member_info")
@AllArgsConstructor
@NoArgsConstructor
public class EntityMemberInfo {

    @Id
    private String userid;
    private String pwd;
    private String nick;
    private String mail;
    private Date regdate;
}
