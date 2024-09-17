package com.devsm.nextpro.auth.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequestDto {
    private String username;
    private String password;
}
