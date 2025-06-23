package com.miniprogram_frame.miniservice4.factory;

import com.miniprogram_frame.miniservice4.domain.*;
import java.time.LocalDateTime;

public class TaskFactory {
    public static Task create(String title, String description, TaskPriority priority) {
        Task task = Task.create();
        task.setTitle(title);
        task.setDescription(description);
        task.setPriority(priority);
        task.setStatus(TaskStatus.TODO);
        task.setCreatedAt(LocalDateTime.now());
        return task;
    }
}
