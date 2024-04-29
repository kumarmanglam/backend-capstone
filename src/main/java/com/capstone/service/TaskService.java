package com.capstone.service;

import com.capstone.dto.TaskDto;
import com.capstone.entity.Task;

import java.util.List;

public interface TaskService {
    Task addTask(TaskDto dto);
    List<Task> getTasks();
    Task getTaskById(long id);
    Task updateTask(long id, TaskDto dto);
    void deleteTask(long id);
}
