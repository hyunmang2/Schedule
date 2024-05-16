package com.sparta.schedule.controller;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.service.ScheduleService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController// 컨트롤러라는 것을 명시하고 html을 반환하지 않는다.
@RequestMapping("/api") // 중복되는 패쓰 api
public class ScheduleController {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/schedules") // 할 일 생성하기 위해 POST
    // 데이터는 바디 부분에 제이슨 형태로 넘어온다! (@RequestBody)
    // requestDto에는 클라이언트로부터 받아온 데이터가 들어있다.
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        return scheduleService.createSchedule(requestDto);
    }

    @GetMapping("/schedules")
    public List<ScheduleResponseDto> getSchedules() {
        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        return scheduleService.getSchedules();
    }

    @PutMapping("/scheduls/{id}")
    public Long updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto) {
        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        return scheduleService.updateSchedule(id, requestDto);
    }

    @DeleteMapping("/schedules/{id}")
    public Long delelteSchedul(@PathVariable Long id) {
        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        return scheduleService.delelteSchedul(id);
    }
}
