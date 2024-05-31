package com.sparta.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentsRequestDto {
    private String comment;
    private String userid;
}
