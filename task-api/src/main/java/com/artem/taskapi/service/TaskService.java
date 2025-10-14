package com.artem.taskapi.service;

import com.artem.taskapi.dto.CreateTaskDto;
import com.artem.taskapi.dto.RespTaskDto;
import com.artem.taskapi.dto.UserDto;
import com.artem.taskapi.entity.Task;
import com.artem.taskapi.entity.TaskStatus;
import com.artem.taskapi.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;


    public List<RespTaskDto> getAllTasks(Long userId) {

        List<Task> allByOwnerId = taskRepository.findAllByOwnerId(userId);

        return allByOwnerId.stream().map(task -> modelMapper.map(task, RespTaskDto.class)).toList();
    }

    public RespTaskDto createTask(CreateTaskDto createTaskDto, Long id) {

        createTaskDto.setOwner(UserDto.builder()
                .id(id)
                .build());
        createTaskDto.setStatus(TaskStatus.IN_PROGRESS);

        Task savedTask = taskRepository.save(modelMapper.map(createTaskDto, Task.class));

        return modelMapper.map(savedTask, RespTaskDto.class);
    }
}
