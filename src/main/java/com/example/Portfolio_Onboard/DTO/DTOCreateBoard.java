package com.example.Portfolio_Onboard.DTO;

import com.example.Portfolio_Onboard.Entity.EntityMemberInfo;
import com.example.Portfolio_Onboard.Entity.EntityWorld;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Data
public class DTOCreateBoard {

    @NotNull(message = "bidx is null")
    private Long bidx;

    @NotBlank(message = "userid is null")
    private String userid;

    @NotBlank(message = "nick is null")
    private String nick;

    @NotBlank(message = "place is null")
    private String place;

    @NotBlank(message = "b_name is null")
    @Pattern(regexp = "^(?=.{1,30}$)\\S+(?: \\S+)*$", message = "invalid boardName")
    private String b_name;

    @NotNull(message = "image is null")
    private MultipartFile files;

    @NotBlank(message = "intro is null")
    @Pattern(regexp = "^.{1,100}$", message = "invalid intro")
    private String intro;

    @NotBlank(message = "reason is null")
    @Pattern(regexp = "^.{1,100}$", message = "invalid reason")
    private String reason;

    @NotNull(message = "regdate is null")
    private Date regdate;

    public EntityWorld entityWorld(EntityMemberInfo memberInfo, String file){

        return new EntityWorld(null, memberInfo, nick, place, b_name, intro, file, reason, new Date(), null);
        // 새로운 EntityWorld(entity)객체를 생성하고, 초기화해 준다.
    }
}
