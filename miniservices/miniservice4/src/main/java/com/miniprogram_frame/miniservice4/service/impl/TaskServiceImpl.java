package com.miniprogram_frame.miniservice4.service.impl;

import com.miniprogram_frame.miniservice4.domain.*;
import com.miniprogram_frame.miniservice4.repository.TaskRepository;
import com.miniprogram_frame.miniservice4.service.TaskService;
import com.miniprogram_frame.miniservice4.factory.TaskFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task addTask(String title, String description, TaskPriority priority) {
        Task task = TaskFactory.create(title, description, priority);
        return taskRepository.save(task);
    }

    @Override
    public Task getTask(Integer id) {
        Optional<Task> task = taskRepository.findById(id);
        return task.orElse(null);
    }

    @Override
    public List<Task> findByStatus(TaskStatus status) {
        return taskRepository.findAll()
                .stream()
                .filter(task -> task.getStatus() == status)
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }
}
