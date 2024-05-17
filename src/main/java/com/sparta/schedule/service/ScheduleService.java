package com.sparta.schedule.service;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.repository.ScheduleRepository;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ScheduleService {


    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;

    }

    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        // DB에 저장하기 위해 ScheduleRequestDto -> Entity 로 변환
        Schedule schedule = new Schedule(requestDto);

        // DB 저장
        Schedule saveSchedule =  scheduleRepository.svae(schedule);

        // Entity -> ResponseDto
        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedule); //????
        return scheduleResponseDto;
    }

    public List<ScheduleResponseDto> getSchedules() {
        // DB 조회
        return scheduleRepository.findAll();
    }
    // todo 다시보세요 세터 쓰는 법, 수정 후 값을 디비에서 가져와서 리턴해주는 거
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto) {

        // 해당 스케쥴이 DB에 존재하는지 확인
        Schedule schedule = scheduleRepository.findById(id); // 수정 전

        if (schedule != null) {
            // schedule 내용 수정
            checkPassword(schedule.getPassword(), requestDto.getPassword());
            scheduleRepository.update(id, requestDto);

            schedule.setTitle(requestDto.getTitle());
            schedule.setContents(requestDto.getContents());
            schedule.setUsername(requestDto.getUsername());
            schedule.setPassword(requestDto.getPassword());
            schedule.setDate(requestDto.getDate());

            ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedule);
            return scheduleResponseDto;
        }else {
            throw  new IllegalArgumentException("선택한 스케쥴은 존재하지 않습니다.");
        }
    }

    public Long delelteSchedul(Long id, ScheduleRequestDto requestDto) {

        // 해당 메모가 DB에 존재하는지 확인
        Schedule schedule = scheduleRepository.findById(id);

        if (schedule != null) {
            // 메모 삭제
            checkPassword(schedule.getPassword(), requestDto.getPassword());
            scheduleRepository.delete(id);

            return id;
        }else {
            throw new IllegalArgumentException("선택한 스케쥴은 존재하지 않습니다.");
        }
    }
    public void checkPassword(String password, String inputPassword) {
        if (!password.equals(inputPassword)) {
            throw new IllegalArgumentException("올바른 비밀번호를 입력해주세요");
        }
    }

}



