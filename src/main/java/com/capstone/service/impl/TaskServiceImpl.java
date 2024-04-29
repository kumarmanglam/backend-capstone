package com.capstone.service.impl;

import com.capstone.dto.TaskDto;
import com.capstone.entity.Task;
import com.capstone.exception.ResourceNotFoundException;
import com.capstone.mapper.TaskMapper;
import com.capstone.repository.TaskRepository;
import com.capstone.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository repository;
    @Override
    public Task addTask(TaskDto dto) {
        try{
            return repository.save(TaskMapper.TaskDtoToTask(dto));
        }catch (Exception e){
            throw new ResourceNotFoundException("Could not add Task");
        }
    }

    @Override
    public List<Task> getTasks() {
        return repository.findAll();
    }

    @Override
    public Task getTaskById(long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Task exists with id: " + id));
    }

    @Override
    public Task updateTask(long id, TaskDto dto) {
        Optional<Task> task = repository.findById(id);
        if(task.isPresent()){
            Task t = new Task(id, dto.getTitle(), dto.getDescription(), dto.getCompleted());
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
