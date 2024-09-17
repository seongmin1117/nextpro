package com.devsm.nextpro.auth.controller;

import com.devsm.nextpro.auth.dto.SignInRequestDto;
import com.devsm.nextpro.auth.dto.SignUpRequestDto;
import com.devsm.nextpro.auth.service.AuthService;
import com.devsm.nextpro.global.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @GetMapping("health-check")
    public String healthCheck() {
        return "ok";
    }

    @PostMapping("sign-up")
    public ResponseEntity<ResponseDto<?>> signUp(@RequestBody SignUpRequestDto dto){
        ResponseDto<?> response = authService.signUp(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("sign-in")
    public ResponseEntity<ResponseDto<?>> signIn(@RequestBody SignInRequestDto dto){
        ResponseDto<?> response = authService.singIn(dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
