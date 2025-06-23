package com.miniprogram_frame.miniservice4.repository;

import com.miniprogram_frame.miniservice4.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
