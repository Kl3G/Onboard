package com.example.Portfolio_Onboard.DTO;

import com.example.Portfolio_Onboard.Entity.EntityMemberInfo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Date;

@Data
public class DTOJoin {

    @NotBlank(message = "ID는 필수 입력입니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)[a-z0-9]{5,15}$", message = "ID 형식이 올바르지 않습니다.")
    private String userid;

    @NotBlank(message = "비밀번호는 필수 입력입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z0-9\\s]).{8,20}$", message = "비밀번호 정책에 맞지 않습니다.")
    private String pwd;

    @NotBlank(message = "닉네임은 필수 입력입니다.")
    @Pattern(regexp = "^[A-Za-z0-9가-힣ㄱ-ㅎぁ-んァ-ン一-龯]{2,10}$", message = "닉네임 형식이 올바르지 않습니다.")
    private String nick;

    @NotBlank(message = "이메일은 필수 입력입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String mail;

    private Date regdate;

    public EntityMemberInfo entityMemberInfo(){

        return new EntityMemberInfo(userid, pwd, nick, mail, new Date(), null);
    }
}
