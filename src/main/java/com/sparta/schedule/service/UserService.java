package com.sparta.schedule.service;

import com.sparta.schedule.dto.LoginRequestDto;
import com.sparta.schedule.dto.MessageResponseDto;
import com.sparta.schedule.dto.SignupRequestDto;
import com.sparta.schedule.entity.User;
import com.sparta.schedule.entity.UserRoleEnum;
import com.sparta.schedule.enums.UserStatusEnum;
import com.sparta.schedule.jwt.JwtUtil;
import com.sparta.schedule.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public MessageResponseDto signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }
        // 사용자 등록
        //Todo 인자로 requestDto.getNickname() 이렇게도 넣을 수 있다?
        User user = new User(requestDto.getNickname(), username, password, role);

        userRepository.save(user);

//        SignupResponseDto signupResponseDto = new SignupResponseDto(username, requestDto.getNickname());

        return new MessageResponseDto("회원가입이 완료되었습니다.");
    }

    public MessageResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String nickname = loginRequestDto.getNickname();
        String password = loginRequestDto.getPassword();
        // 회원가입 된 유저인지 확인.
        User user = findByNickname(nickname);
        // 로그인 시 입력한 비밀번호와 입력한 비밀번호가 일치하는지 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        // Token 생성
        String token = jwtUtil.createToken(user.getUsername(), UserRoleEnum.USER);
        // Header에 Token 저장
        jwtUtil.addJwtToHeader(JwtUtil.AUTHORIZATION_HEADER, token, response);

        return new MessageResponseDto("로그인이 완료되었습니다.");
    }

    private User findByNickname(String nickname) {
        return userRepository.findByNickname(nickname).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
    }
}