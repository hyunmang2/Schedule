package com.sparta.schedule.service;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ScheduleService {


    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;

    }

    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        // DB에 저장하기 위해 ScheduleRequestDto -> Entity 로 변환
        Schedule schedule = new Schedule(requestDto);

        // DB 저장
        Schedule saveSchedule = scheduleRepository.save(schedule);

        // Entity -> ResponseDto
        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedule);
        return scheduleResponseDto;
    }

    public List<ScheduleResponseDto> getSchedules() {
        // DB 조회
        return scheduleRepository.findAllByOrderByModifiedAtDesc().stream().map(ScheduleResponseDto::new).toList();
    }

    // todo 다시보세요 세터 쓰는 법, 수정 후 값을 디비에서 가져와서 리턴해주는 거
    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto) {

        // 해당 스케쥴이 DB에 존재하는지 확인
        Schedule schedule = findSchedule(id);// 수정 전

        // schedule 내용 수정
        checkPassword(schedule.getPassword(), requestDto.getPassword());
        schedule.update(requestDto);

        schedule.setTitle(requestDto.getTitle());
        schedule.setContents(requestDto.getContents());
        schedule.setUsername(requestDto.getUsername());
        schedule.setPassword(requestDto.getPassword());

        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedule);
        return scheduleResponseDto;
    }

    @Transactional
    public Long delelteSchedul(Long id, ScheduleRequestDto requestDto) {

        // 해당 메모가 DB에 존재하는지 확인
        Schedule schedule = findSchedule(id);

        // 메모 삭제
        checkPassword(schedule.getPassword(), requestDto.getPassword());
        scheduleRepository.delete(schedule);

        return id;
    }

    public void checkPassword(String password, String inputPassword) {
        if (!password.equals(inputPassword)) {
            throw new IllegalArgumentException("올바른 비밀번호를 입력해주세요");
        }
    }

    private Schedule findSchedule(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("선택한 스케쥴은 존재하지 않습니다."));
    }
}





