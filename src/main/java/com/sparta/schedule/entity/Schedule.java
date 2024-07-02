package com.sparta.schedule.entity;

import com.sparta.schedule.dto.ScheduleRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "schedule")
@NoArgsConstructor
public class Schedule extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 키 자동 증가?
    private Long scheduleId;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "contents", nullable = false, length = 500)
    private String contents;
    @Column(name = "username", nullable = false, length = 20)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "schedule")
    private List<Comments> commentsList = new ArrayList<>();

    public void addcommentsList(Comments comments) {
        this.commentsList.add(comments);
        comments.setSchedule(this); // 외래 키 설정

    }


    public Schedule(ScheduleRequestDto requestDto) {
        // requestDto에 클라이언트로부터 받아온 데이터가 있으니 그걸 토대로 필드에 값을 넣어주기 위해 만듬
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();

    }
    public void update(ScheduleRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.username = requestDto.getUsername();

    }
}
