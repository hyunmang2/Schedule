package com.sparta.schedule.controller;

import com.mysql.cj.x.protobuf.Mysqlx;
import com.sparta.schedule.dto.LoginRequestDto;
import com.sparta.schedule.dto.MessageResponseDto;
import com.sparta.schedule.dto.SignupRequestDto;
import com.sparta.schedule.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/login-page")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/user/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/user/signup")
    public ResponseEntity<MessageResponseDto> signup(@RequestBody @Valid SignupRequestDto requestDto) {
        MessageResponseDto signup = userService.signup(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(signup);
    }

    @PostMapping("/user/login")
    public ResponseEntity<MessageResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        MessageResponseDto login =  userService.login(loginRequestDto, response);
        return ResponseEntity.status(HttpStatus.OK).body(login);
    }
}