package com.miniprogram_frame.miniservice4.service;

import com.miniprogram_frame.miniservice4.domain.*;
import java.util.List;

public interface TaskService {
    Task addTask(String title, String description, TaskPriority priority);
    Task getTask(Integer id);
    List<Task> findByStatus(TaskStatus status);
    List<Task> findAll();
}
