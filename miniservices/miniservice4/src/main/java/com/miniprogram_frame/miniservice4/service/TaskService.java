package com.miniprogram_frame.miniservice4.service;

import java.util.List;

import com.miniprogram_frame.miniservice4.domain.Task;
import com.miniprogram_frame.miniservice4.domain.TaskPriority;
import com.miniprogram_frame.miniservice4.domain.TaskStatus;

public interface TaskService {
  Task addTask(String title, String description, TaskPriority priority);

  Task getTask(Integer id);

  List<Task> findByStatus(TaskStatus status);

  List<Task> findAll();

  <T extends Comparable<? super T>> List<Task> findTopNTasks(List<Task> tasks, int n);
}
