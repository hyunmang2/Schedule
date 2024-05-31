package com.sparta.schedule.service;

import com.sparta.schedule.dto.CommentsRequestDto;
import com.sparta.schedule.dto.CommentsResponseDto;
import com.sparta.schedule.entity.Comments;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.repository.CommentsRepository;
import com.sparta.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentsService {

    private final CommentsRepository commentsRepository;

    private final ScheduleRepository scheduleRepository;


    // 댓글을 scheduleRepository에 저장
    public CommentsResponseDto createComments(Long id, CommentsRequestDto commentsrequestDto) {

        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("["+id+"] 이라는 숫자의 일정이 DB에 저장되어 있지 않습니다.")
                );

        Comments saveComments = commentsRepository.save(new Comments(schedule, commentsrequestDto));
        return new CommentsResponseDto(saveComments);
    }
    @Transactional
    public CommentsResponseDto updateComments(Long commentid, CommentsRequestDto commentsRequestDto) {

        // 해당 스케쥴이 DB에 존재하는지 확인
        Comments comments = findcomments(commentid);// 수정 전

        // 댓글 내용 수정
        comments.update(commentsRequestDto);

        comments.setComment(commentsRequestDto.getComment());

        CommentsResponseDto commentsResponseDto = new CommentsResponseDto(comments);
        return commentsResponseDto;
    }

    @Transactional
    public String delelteComments(Long commentid) {

        // 해당 댓글 DB에 존재하는지 확인
        Comments comments = findcomments(commentid);

        // 댓글 삭제;
        commentsRepository.delete(comments);

        return "[" + commentid + "] 번호가 삭제되었습니다";
    }
    private Comments findcomments(Long commentid) {
        return commentsRepository.findById(commentid).orElseThrow(() -> new IllegalArgumentException("선택한 댓글은 존재하지 않습니다."));
    }
}
