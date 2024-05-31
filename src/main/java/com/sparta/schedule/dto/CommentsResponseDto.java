package com.sparta.schedule.dto;

import com.sparta.schedule.entity.Comments;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentsResponseDto {
    private Long commentid;
    private String comment;
    private String userid;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CommentsResponseDto(Comments comments) {
        this.commentid = comments.getCommentid();
        this.comment = comments.getComment();
        this.userid = comments.getUserid();
        this.createdAt = comments.getCreatedAt();
        this.modifiedAt = comments.getModifiedAt();
    }
}
