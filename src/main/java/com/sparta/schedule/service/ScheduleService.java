package com.sparta.schedule.service;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.repository.ScheduleRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ScheduleService {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        // DB에 저장하기 위해 ScheduleRequestDto -> Entity 로 변환
        Schedule schedule = new Schedule(requestDto);

        // DB 저장
        ScheduleRepository scheduleRepository = new ScheduleRepository(jdbcTemplate);
        Schedule saveSchedule =  scheduleRepository.svae(schedule);

        // Entity -> ReponseDto
        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedule);
        return scheduleResponseDto;
    }

    public List<ScheduleResponseDto> getSchedules() {
        // DB 조회
        ScheduleRepository scheduleRepository = new ScheduleRepository(jdbcTemplate);
        return scheduleRepository.findAll();
    }

    public Long updateSchedule(Long id, ScheduleRequestDto requestDto) {

        ScheduleRepository scheduleRepository = new ScheduleRepository(jdbcTemplate);
        // 해당 스케쥴이 DB에 존재하는지 확인
        Schedule schedule = scheduleRepository.findById(id);
        if (schedule != null) {
            // schedule 내용 수정
            scheduleRepository.update(id, requestDto);

            return id;
        }else {
            throw  new IllegalArgumentException("선택한 스케쥴은 존재하지 않습니다.");
        }
    }

    public Long delelteSchedul(Long id) {
        ScheduleRepository scheduleRepository = new ScheduleRepository(jdbcTemplate);
        // 해당 메모가 DB에 존재하는지 확인
        Schedule schedule = scheduleRepository.findById(id);
        if (schedule != null) {
            // 메모 삭제
            scheduleRepository.delete(id);

            return id;
        }else {
            throw new IllegalArgumentException("선택한 스케쥴은 존재하지 않습니다.");
        }
    }
}
