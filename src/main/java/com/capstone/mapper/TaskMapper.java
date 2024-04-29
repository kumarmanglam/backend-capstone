package com.capstone.mapper;

import com.capstone.dto.TaskDto;
import com.capstone.entity.Task;

public class TaskMapper {
    public static Task TaskDtoToTask(TaskDto t){
        return new Task(t.getId(), t.getTitle(), t.getDescription(),t.getCompleted());
    }


    public static TaskDto TaskToTaskDto(Task t){
        return new TaskDto(t.getId(), t.getTitle(), t.getDescription(),t.getCompleted());
    }
}
