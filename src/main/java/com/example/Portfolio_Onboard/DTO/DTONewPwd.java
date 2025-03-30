package com.example.Portfolio_Onboard.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class DTONewPwd {

    @NotBlank(message = "mail is null")
    private String mail;

    @NotBlank(message = "userid is null")
    private String userid;

    @NotBlank(message = "newPwd is null")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z0-9\\s]).{8,20}$", message = "invalid pwd")
    private String newPwd;
}
