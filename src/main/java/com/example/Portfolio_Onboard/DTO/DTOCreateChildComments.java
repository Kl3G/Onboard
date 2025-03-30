package com.example.Portfolio_Onboard.DTO;

import com.example.Portfolio_Onboard.Entity.EntityChildComments;
import com.example.Portfolio_Onboard.Entity.EntityComments;
import com.example.Portfolio_Onboard.Entity.EntityMemberInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Date;

@Data
public class DTOCreateChildComments {

    @NotNull(message = "cidx is null")
    private Long cidx;

    @NotBlank(message = "userid is null")
    private String userid;

    @NotBlank(message = "ccpwd is null")
    @Pattern(regexp = "^\\d{4}$", message = "invalid ccpwd")
    private String ccpwd;

    @NotBlank(message = "nick is null")
    private String nick;

    @NotBlank(message = "text is null")
    @Pattern(regexp = "^(?=.{1,500}$)(?!^\\s+$).*$", message = "invalid ccText")
    private String text;

    @NotBlank(message = "userip is null")
    private String userip;

    public EntityChildComments setEntityChildComments(EntityComments comment, EntityMemberInfo memberInfo) {

        return new EntityChildComments(null, comment, memberInfo, ccpwd, nick, text, userip, new Date());
    }
}
