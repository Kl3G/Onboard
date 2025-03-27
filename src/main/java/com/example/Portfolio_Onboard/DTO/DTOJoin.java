package com.example.Portfolio_Onboard.DTO;

import com.example.Portfolio_Onboard.Entity.EntityMemberInfo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Date;

@Data
public class DTOJoin {

    @NotBlank(message = "userid is null")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)[a-z0-9]{5,15}$", message = "invalid userid")
    private String userid;

    @NotBlank(message = "pwd is null")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z0-9\\s]).{8,20}$", message = "invalid pwd")
    private String pwd;

    @NotBlank(message = "nick is null")
    @Pattern(regexp = "^[A-Za-z0-9가-힣ㄱ-ㅎぁ-んァ-ン一-龯]{2,10}$", message = "invalid nick")
    private String nick;

    @NotBlank(message = "mail is null")
    @Email(message = "invalid mail")
    private String mail;

    @NotNull(message = "regdate is null")
    private Date regdate;

    public EntityMemberInfo entityMemberInfo(){

        return new EntityMemberInfo(userid, pwd, nick, mail, new Date(), null);
    }
}
