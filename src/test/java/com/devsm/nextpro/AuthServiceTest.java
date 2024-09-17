package com.devsm.nextpro;

import com.devsm.nextpro.auth.dto.SignInRequestDto;
import com.devsm.nextpro.auth.dto.SignUpRequestDto;
import com.devsm.nextpro.auth.jwt.JwtProvider;
import com.devsm.nextpro.auth.service.AuthService;
import com.devsm.nextpro.global.ResponseDto;
import com.devsm.nextpro.user.entity.User;
import com.devsm.nextpro.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private JwtProvider jwtProvider;

    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authService = new AuthService(userRepository, bCryptPasswordEncoder, jwtProvider);
    }

    // 회원가입 테스트
    @Test
    @DisplayName("회원가입 성공시 성공한다.")
    void testSignUp_Success() {
        // given
        String username = "testuser";
        String password = "password";
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto();
        signUpRequestDto.setUsername(username);
        signUpRequestDto.setPassword(password);

        when(userRepository.existsByUsername(username)).thenReturn(false);
        when(bCryptPasswordEncoder.encode(password)).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        ResponseDto<Long> response = authService.signUp(signUpRequestDto);

        // then
        assertThat(response.getCode()).isEqualTo("SU");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("중복된 이메일로 회원가입을 시도하면 실패한다.")
    void testSignUp_DuplicateUsername() {
        // given
        String username = "testuser";
        String password = "password";
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto();
        signUpRequestDto.setUsername(username);
        signUpRequestDto.setPassword(password);

        when(userRepository.existsByUsername(username)).thenReturn(true);

        // when & then
        assertThatThrownBy(() -> authService.signUp(signUpRequestDto))
                .isInstanceOf(RuntimeException.class);
        verify(userRepository, never()).save(any(User.class));
    }

    // 로그인 테스트
    @Test
    @DisplayName("로그인 성공시 JWT 토큰을 반환한다.")
    void testSignIn_Success() {
        // given
        String username = "testuser";
        String password = "password";
        String encodedPassword = "encodedPassword";
        String uuid = UUID.randomUUID().toString();
        String role = "USER";

        SignInRequestDto signInRequestDto = new SignInRequestDto();
        signInRequestDto.setUsername(username);
        signInRequestDto.setPassword(password);

        User user = User.builder()
                .username(username)
                .password(encodedPassword)
                .uuid(uuid)
                .role(role)
                .build();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(bCryptPasswordEncoder.matches(password, encodedPassword)).thenReturn(true);
        when(jwtProvider.create(username, uuid, role)).thenReturn("mockJwtToken");

        // when
        ResponseDto<?> response = authService.singIn(signInRequestDto);

        // then
        assertThat(response.getCode()).isEqualTo("SU");
        assertThat(response.getData()).isEqualTo("mockJwtToken");
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    @DisplayName("잘못된 비밀번호로 로그인 시도 시 실패한다.")
    void testSignIn_InvalidPassword() {
        // given
        String username = "testuser";
        String password = "password";
        String encodedPassword = "wrongPassword";

        SignInRequestDto signInRequestDto = new SignInRequestDto();
        signInRequestDto.setUsername(username);
        signInRequestDto.setPassword(password);

        User user = User.builder()
                .username(username)
                .password(encodedPassword)
                .uuid(UUID.randomUUID().toString())
                .role("USER")
                .build();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(bCryptPasswordEncoder.matches(password, encodedPassword)).thenReturn(false);

        // when & then
        assertThatThrownBy(() -> authService.singIn(signInRequestDto))
                .isInstanceOf(RuntimeException.class);
        verify(userRepository, times(1)).findByUsername(username);
    }
}
