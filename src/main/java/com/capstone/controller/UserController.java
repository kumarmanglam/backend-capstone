package com.capstone.controller;

import com.capstone.dto.TaskDto;
import com.capstone.dto.UserDto;
import com.capstone.entity.Task;
import com.capstone.entity.User;
import com.capstone.mapper.TaskMapper;
import com.capstone.utility.AuthUtility;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    AuthUtility authUtility;
    @GetMapping
    public ResponseEntity<UserDto> getTasks(){
        User user = authUtility.getUserByUsername();
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
