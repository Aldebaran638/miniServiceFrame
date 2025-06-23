package com.miniprogram_frame.miniservice4.controller;

import com.miniprogram_frame.miniservice4.domain.*;
import com.miniprogram_frame.miniservice4.service.TaskService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public String addTask(@RequestParam String title, @RequestParam String description, @RequestParam TaskPriority priority) {
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
