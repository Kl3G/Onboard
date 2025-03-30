package com.example.Portfolio_Onboard.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class DTOModifyPost {

    private Long pidx;

    @NotBlank(message = "ppwd is null")
    @Pattern(regexp = "^\\d{4}$", message = "invalid ppwd")
    private String ppwd;

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
}
