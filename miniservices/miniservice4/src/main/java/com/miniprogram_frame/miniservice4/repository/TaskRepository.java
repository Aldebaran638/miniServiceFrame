package com.miniprogram_frame.miniservice4.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miniprogram_frame.miniservice4.domain.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
