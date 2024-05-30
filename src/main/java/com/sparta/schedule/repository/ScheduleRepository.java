package com.sparta.schedule.repository;

import com.sparta.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

        List<Schedule> findAllByOrderByModifiedAtDesc(); // 정렬해서 전체 데이터를 내보낼 때 내림차순
}
