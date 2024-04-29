package com.capstone.controller;

import com.capstone.dto.TaskDto;
import com.capstone.entity.Task;
import com.capstone.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@CrossOrigin("*")
public class TaskController {
    @Autowired
    TaskService service;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskDto dto){
        System.out.println("dto receive is  " + dto);
        return new ResponseEntity<>(service.addTask(dto), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<Task>> getTasks(){
        return new ResponseEntity<>(service.getTasks(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable("id") long id){
        return new ResponseEntity<>(service.getTaskById(id), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable("id") long id, @RequestBody TaskDto dto){
        return new ResponseEntity<>(service.updateTask(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable("id") long id){
        service.deleteTask(id);
        return new ResponseEntity<>( HttpStatus.OK);
    }
}
