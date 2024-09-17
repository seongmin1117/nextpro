package com.devsm.nextpro.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequestDto {
    private String username;
    private String password;
}
