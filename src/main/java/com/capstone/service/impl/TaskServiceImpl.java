package com.capstone.service.impl;

import com.capstone.dto.TaskDto;
import com.capstone.entity.Task;
import com.capstone.entity.User;
import com.capstone.exception.ResourceNotFoundException;
import com.capstone.mapper.TaskMapper;
import com.capstone.repository.TaskRepository;
import com.capstone.service.TaskService;
import com.capstone.utility.AuthUtility;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
    TaskRepository repository;

    AuthUtility authUtility;

    @Override
    public Task addTask(TaskDto dto) {
        try{
            Task task = TaskMapper.TaskDtoToTask(dto);
            User userByUsername = authUtility.getUserByUsername();
            task.setUser(userByUsername);
            return repository.save(task);
        }catch (Exception e){
            e.printStackTrace();
            throw new ResourceNotFoundException("Could not add Task");
        }
    }

    @Override
    public List<Task> getTasks() {
        User userByUsername = authUtility.getUserByUsername();
        return repository.findAllByUserId(userByUsername.getId());
    }

    @Override
    public Task getTaskById(long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Task exists with id: " + id));
    }

    @Override
    public Task updateTask(long id, TaskDto dto) {
        Optional<Task> task = repository.findById(id);
        if(task.isPresent()){
            Task t = new Task(id, dto.getTitle(), dto.getDescription(), dto.getCompleted(), authUtility.getUserByUsername());
            return repository.save(t);
        }else{
            throw new ResourceNotFoundException("No Task exists with given id");
        }
    }

    @Override
    public void deleteTask(long id) {
        Optional<Task> task = repository.findById(id);
        if(task.isPresent()){
            repository.delete(task.get());
        }else{
            throw new ResourceNotFoundException("No Task exists with given id");
        }
    }
}
