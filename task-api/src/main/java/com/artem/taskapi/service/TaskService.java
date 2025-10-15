package com.artem.taskapi.service;

import com.artem.taskapi.dto.CreateTaskDto;
import com.artem.taskapi.dto.RespTaskDto;
import com.artem.taskapi.dto.UserDto;
import com.artem.taskapi.entity.Task;
import com.artem.taskapi.entity.TaskStatus;
import com.artem.taskapi.exception.TaskNotFoundException;
import com.artem.taskapi.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

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

    public RespTaskDto getTaskById(Long id) {
        return taskRepository.findById(id)
                .map(task -> modelMapper.map(task, RespTaskDto.class))
                .orElseThrow(TaskNotFoundException::new);
    }


    public void deleteTask(Long id) {

        Optional<Task> maybeTask = taskRepository.findById(id);

        if (maybeTask.isEmpty()) {
            throw new TaskNotFoundException();
        }

        taskRepository.delete(maybeTask.get());
    }

    public RespTaskDto editTask(CreateTaskDto createTaskDto) {


        Task task = taskRepository.findById(createTaskDto.getId()).orElseThrow(TaskNotFoundException::new);

        task.setTitle(createTaskDto.getTitle());
        task.setDescription(createTaskDto.getDescription());

        Task savedTask = taskRepository.save(task);

        return modelMapper.map(savedTask, RespTaskDto.class);
    }

    public RespTaskDto makeTaskDone(Long id) {

        Task task = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);

        task.setStatus(TaskStatus.DONE);
        task.setDone_at(LocalDateTime.now());
        Task savedTask = taskRepository.save(task);

        return modelMapper.map(savedTask, RespTaskDto.class);
    }

    public RespTaskDto makeTasCancelled(Long id) {

        Task task = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);

        task.setStatus(TaskStatus.CANCELLED);

        Task savedTask = taskRepository.save(task);

        return modelMapper.map(savedTask, RespTaskDto.class);
    }
}
