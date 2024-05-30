package com.sparta.schedule.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 추상클래스에 선언한 멤버 변수를 컬럼으로 인식을 하게 해준다.
@EntityListeners(AuditingEntityListener.class) // 해당 클래스에 Auditing 기능을 수행하게 해준다.
public abstract class Timestamped {

    @CreatedDate
    @Column(updatable = false) // 최초 생성후 업데이트 불가
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @LastModifiedDate // 스케쥴 수정 후 수정 시간으로 변경
    @Column
    @Temporal(TemporalType.TIMESTAMP) // 2024-05-30 15:53:20.000001 처럼 보여준다.
    private LocalDateTime modifiedAt;
}