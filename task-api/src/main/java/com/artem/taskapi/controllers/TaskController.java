package com.artem.taskapi.controllers;

import com.artem.taskapi.dto.CreateTaskDto;
import com.artem.taskapi.dto.RespTaskDto;
import com.artem.taskapi.security.UserDetailsImpl;
import com.artem.taskapi.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController()
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping()
    public ResponseEntity<List<RespTaskDto>> getAllUserTasks(@AuthenticationPrincipal UserDetailsImpl userDetails){

        return ResponseEntity.ok(taskService.getAllTasks(userDetails.getId()));

    }

    @PostMapping()
    public ResponseEntity<RespTaskDto> createTask(@RequestBody CreateTaskDto createTaskDto, @AuthenticationPrincipal UserDetailsImpl userDetails){

        return ResponseEntity.ok(taskService.createTask(createTaskDto, userDetails.getId()));
    }
}
