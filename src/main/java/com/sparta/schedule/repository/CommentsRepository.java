package com.sparta.schedule.repository;

import com.sparta.schedule.entity.Comments;
import com.sparta.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository <Comments, Long>{
    List<Comments> findAllByOrderByModifiedAtDesc();
}
