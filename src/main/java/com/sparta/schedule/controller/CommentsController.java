package com.sparta.schedule.controller;


import com.sparta.schedule.dto.CommentsRequestDto;
import com.sparta.schedule.dto.CommentsResponseDto;
import com.sparta.schedule.service.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController// 컨트롤러라는 것을 명시하고 html을 반환하지 않는다.
@RequiredArgsConstructor
@RequestMapping("/api") // 중복되는 패쓰 api
public class CommentsController {

    private final CommentsService commentsService;

    @PostMapping("/comments/{id}")
    public CommentsResponseDto createComments(@PathVariable Long id, @RequestBody CommentsRequestDto commentsrequestDto) {
        if (id == null) {
            throw new IllegalArgumentException("고유번호를 입력해주세요.");
        }
        return commentsService.createComments(id, commentsrequestDto);
    }

    @PutMapping("/comments/{commentid}")
    public CommentsResponseDto updateComments(@PathVariable Long commentid, @RequestBody CommentsRequestDto commentsRequestDto) {
        return commentsService.updateComments(commentid, commentsRequestDto);
    }

    @DeleteMapping("/comments/{commentid}")
    public String delelteComments(@PathVariable Long commentid) {
        return commentsService.delelteComments(commentid);
    }

}
