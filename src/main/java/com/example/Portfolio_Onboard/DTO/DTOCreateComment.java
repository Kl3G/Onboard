package com.example.Portfolio_Onboard.DTO;

import com.example.Portfolio_Onboard.Entity.EntityComments;
import com.example.Portfolio_Onboard.Entity.EntityMemberInfo;
import com.example.Portfolio_Onboard.Entity.EntityPost;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Date;

@Data
public class DTOCreateComment {

    @NotNull(message = "pidx is null")
    private Long pidx;

    @NotBlank(message = "userid is null")
    private String userid;

    @NotBlank(message = "cpwd is null")
    @Pattern(regexp = "^\\d{4}$", message = "invalid cpwd")
    private String cpwd;

    @NotBlank(message = "nick is null")
    private String nick;

    @NotBlank(message = "text is null")
    @Pattern(regexp = "^(?=.{1,500}$)(?!^\\s+$).*$", message = "invalid cText")
    private String text;

    @NotBlank(message = "userip is null")
    private String userip;

    public EntityComments entityComment(EntityPost post, EntityMemberInfo memberInfo){

        return new EntityComments(null, post, memberInfo, cpwd, nick, text, userip, new Date(), null);
    }
}
