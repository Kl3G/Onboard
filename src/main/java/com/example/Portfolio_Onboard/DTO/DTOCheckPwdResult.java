package com.example.Portfolio_Onboard.DTO;

import com.example.Portfolio_Onboard.Entity.EntityMemberInfo;
import lombok.Data;

@Data
public class DTOCheckPwdResult {

    private String result;
    private EntityMemberInfo entityMemberInfo;
}
