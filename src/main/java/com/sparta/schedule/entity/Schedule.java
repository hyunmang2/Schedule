package com.sparta.schedule.entity;

import com.sparta.schedule.dto.ScheduleRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Schedule {

    private Long id;
    private String title;
    private String contents;
    private String username;
    private String password;
    private String date;

    public Schedule(ScheduleRequestDto requestDto) {
        // requestDto에 클라이언트로부터 받아온 데이터가 있으니 그걸 토대로 필드에 값을 넣어주기 위해 만듬
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
        this.date = requestDto.getDate();

    }
}
