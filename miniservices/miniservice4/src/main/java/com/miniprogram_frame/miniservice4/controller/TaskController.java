package com.miniprogram_frame.miniservice4.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miniprogram_frame.miniservice4.domain.Task;
import com.miniprogram_frame.miniservice4.domain.TaskPriority;
import com.miniprogram_frame.miniservice4.domain.TaskStatus;
import com.miniprogram_frame.miniservice4.service.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {
  private final TaskService taskService;

  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @PostMapping
  public String addTask(@RequestParam String title, @RequestParam String description,
      @RequestParam TaskPriority priority) {
    taskService.addTask(title, description, priority);
    return "Saved";
  }

  @GetMapping("/{id}")
  public Task getTask(@PathVariable Integer id) {
    return taskService.getTask(id);
  }

  @GetMapping
  public List<Task> getTasksByStatus(@RequestParam TaskStatus status) {
    return taskService.findByStatus(status);
  }
}
