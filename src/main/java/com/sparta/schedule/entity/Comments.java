package com.sparta.schedule.entity;

import com.sparta.schedule.dto.CommentsRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "comments")
@NoArgsConstructor

public class Comments extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    @Column(name = "comment", nullable = false,length = 300)
    private String comment;
    @Column(name = "userid", nullable = false)
    private String userid;

    @ManyToOne
    @JoinColumn(name = "schedule_id") // 외래키
    private Schedule schedule;

    public Comments(Schedule schedule, CommentsRequestDto commentsRequestDto) {
        this.schedule = schedule;
        this.comment = commentsRequestDto.getComment();
        this.userid = commentsRequestDto.getUserid();
    }

    public void update(CommentsRequestDto commentsRequestDto) {
        this.comment = commentsRequestDto.getComment();
    }
}
