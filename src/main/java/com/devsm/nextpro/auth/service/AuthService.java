package com.devsm.nextpro.auth.service;

import com.devsm.nextpro.auth.dto.SignInRequestDto;
import com.devsm.nextpro.auth.dto.SignUpRequestDto;
import com.devsm.nextpro.auth.exception.AuthException;
import com.devsm.nextpro.auth.jwt.JwtProvider;
import com.devsm.nextpro.global.ResponseDto;
import com.devsm.nextpro.user.entity.User;
import com.devsm.nextpro.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtProvider jwtProvider;

    //회원가입
    public ResponseDto<Long> signUp(SignUpRequestDto dto){
        String username = dto.getUsername();
        if (userRepository.existsByUsername(username)) throw new AuthException.DuplicateEmailException();

        String password = dto.getPassword();
        dto.setPassword(bCryptPasswordEncoder.encode(password));

        User user = userRepository.save(User.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .uuid(UUID.randomUUID().toString())
                .build());

        return ResponseDto.success(user.getUserId());
    }

    //로그인
    public ResponseDto<?> singIn(SignInRequestDto dto){

        String username = dto.getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("이메일 또는 비밀번호가 다릅니다."));

        String password = dto.getPassword();
        String encodedPassword = user.getPassword();
        boolean isMatched = bCryptPasswordEncoder.matches(password, encodedPassword);
        if (!isMatched) throw new EntityNotFoundException("이메일 또는 비밀번호가 다릅니다.");

        String uuid = user.getUuid();
        String role = user.getRole();

        String token = jwtProvider.create(username, uuid, role);
        return ResponseDto.success(token);
    }
}
