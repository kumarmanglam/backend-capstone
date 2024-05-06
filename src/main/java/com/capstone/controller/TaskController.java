package com.capstone.controller;

import com.capstone.dto.TaskDto;
import com.capstone.entity.Task;
import com.capstone.exception.ResourceNotFoundException;
import com.capstone.mapper.TaskMapper;
import com.capstone.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/todos")
@CrossOrigin("*")
public class TaskController {
    @Autowired
    TaskService service;

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto dto){
        Task task = service.addTask(dto);
        return new ResponseEntity<>(TaskMapper.TaskToTaskDto(task), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<TaskDto>> getTasks(){
        List<Task> tasks = service.getTasks();
        Collections.reverse(tasks);
        List<TaskDto> taskDtos = tasks.stream().map(TaskMapper::TaskToTaskDto).toList();
        return new ResponseEntity<>(taskDtos, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable("id") long id){
        return new ResponseEntity<>(TaskMapper.TaskToTaskDto(service.getTaskById(id)), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable("id") long id, @RequestBody TaskDto dto){
        return new ResponseEntity<>(TaskMapper.TaskToTaskDto(service.updateTask(id, dto)), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable("id") long id){
        service.deleteTask(id);
        return new ResponseEntity<>("Task Deleted successfully", HttpStatus.OK);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}

