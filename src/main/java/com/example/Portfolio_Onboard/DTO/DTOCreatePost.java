package com.example.Portfolio_Onboard.DTO;

import com.example.Portfolio_Onboard.Entity.EntityFiles;
import com.example.Portfolio_Onboard.Entity.EntityPost;
import com.example.Portfolio_Onboard.Entity.EntityWorld;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import java.util.Date;

@Data
public class DTOCreatePost {

    private Long pidx;

    @NotNull(message = "bidx is null")
    private Long bidx; // 보드 외래키

    @NotBlank(message = "userid is null")
    private String userid; // 멤버 외래키

    // newdate는 입력 안 함, 수정할 때 입력
    // private Long view_count 입력 안 함
    // private Long good_count 입력 안 함
    @NotBlank(message = "ppwd is null")
    @Pattern(regexp = "^\\d{4}$", message = "invalid ppwd")
    private String ppwd;

    @NotBlank(message = "nick is null")
    private String nick;

    @NotBlank(message = "category is null")
    private String category;

    @NotBlank(message = "title is null")
    @Pattern(regexp = "^(?!\\s)(?=.{1,25}$)(?!.*\\s$).*$", message = "invalid title")
    private String title;

    @NotBlank(message = "text is null")
    @Pattern(regexp = "^(?=.{1,3100}$)(?!^\\s+$).*$", message = "invalid text")
    private String text;

    @NotBlank(message = "userip is null")
    private String userip;

    private MultipartFile[] files;

    public EntityPost entityPost(EntityWorld board, EntityFiles entityFiles){

        return new EntityPost(null, board, userid, ppwd, nick, category, title, text, userip, new Date(), null, 0L, 0L, null, entityFiles);
    }
}
