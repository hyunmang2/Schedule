package com.sparta.schedule.dto;

import com.sparta.schedule.entity.UserRoleEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    private Long id;
    private String nickname;
    private String username;
    private String password;
    private boolean admin = false;
    private String adminToken = "";
    private UserRoleEnum role;
}