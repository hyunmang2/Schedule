package com.sparta.schedule.dto;


import com.sparta.schedule.entity.Schedule;
import lombok.Getter;

@Getter
public class ScheduleResponseDto {
    private Long id;
    private String title;
    private String contents;
    private String username;
    private String password;
    private String date;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.username = schedule.getUsername();
        this.password = schedule.getPassword();
        this.date = schedule.getDate();
    }

    public ScheduleResponseDto(Long id, String title, String contents, String username, String password, String date) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.username = username;
        this.password = password;
        this.date = date;
    }
}
