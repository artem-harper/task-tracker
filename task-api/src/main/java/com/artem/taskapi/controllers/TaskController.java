package com.artem.taskapi.controllers;

import com.artem.taskapi.dto.CreateTaskDto;
import com.artem.taskapi.dto.RespTaskDto;
import com.artem.taskapi.entity.TaskStatus;
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
    public ResponseEntity<List<RespTaskDto>> getAllUserTasks(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok(taskService.getAllTasks(userDetails.getId()));

    }

    @GetMapping("/{id}")
    public ResponseEntity<RespTaskDto> getTaskById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PostMapping()
    public ResponseEntity<RespTaskDto> createTask(@RequestBody CreateTaskDto createTaskDto,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok(taskService.createTask(createTaskDto, userDetails.getId()));
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<RespTaskDto> makeTaskDone(@PathVariable("id") Long id) {

        return ResponseEntity.ok(taskService.makeTaskDone(id));
    }

    @PatchMapping("/{id}/cancelled")
    public ResponseEntity<RespTaskDto> makeTaskCancelled(@PathVariable("id") Long id) {

        return ResponseEntity.ok(taskService.makeTasCancelled(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") Long id) {

        taskService.deleteTask(id);

        return ResponseEntity.noContent().build();

    }

    @PatchMapping()
    public ResponseEntity<RespTaskDto> editTask(@RequestBody CreateTaskDto createTaskDto) {

        createTaskDto.setStatus(TaskStatus.IN_PROGRESS);

        return ResponseEntity.ok(taskService.editTask(createTaskDto));
    }
}
