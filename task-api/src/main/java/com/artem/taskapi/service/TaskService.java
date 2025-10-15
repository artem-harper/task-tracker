package com.artem.taskapi.service;

import com.artem.taskapi.dto.CreateTaskDto;
import com.artem.core.CreatedTaskEvent;
import com.artem.taskapi.dto.RespTaskDto;
import com.artem.taskapi.entity.Task;
import com.artem.taskapi.entity.TaskStatus;
import com.artem.taskapi.exception.TaskNotFoundException;
import com.artem.taskapi.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;
    private final KafkaTemplate<Long, CreatedTaskEvent> kafkaTemplate;
    private final UserService userService;

    public List<RespTaskDto> getAllTasks(Long userId) {

        List<Task> allByOwnerId = taskRepository.findAllByOwnerId(userId);

        return allByOwnerId.stream().map(task -> modelMapper.map(task, RespTaskDto.class)).toList();
    }

    public RespTaskDto createTask(CreateTaskDto createTaskDto, Long id) {

        createTaskDto.setOwner(userService.findById(id));
        createTaskDto.setStatus(TaskStatus.IN_PROGRESS);

        Task savedTask = taskRepository.save(modelMapper.map(createTaskDto, Task.class));

        sendMessageToTopic(savedTask);

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

    public void sendMessageToTopic(Task savedTask) {

        CreatedTaskEvent createdTaskEvent = CreatedTaskEvent.builder()
                .title(savedTask.getTitle())
                .description(savedTask.getDescription())
                .emailOwner(savedTask.getOwner().getEmail())
                .createdAt(LocalDateTime.now())
                .build();

        SendResult<Long, CreatedTaskEvent> result = null;
        try {
            result = kafkaTemplate.send("EMAIL_SENDING_TASKS", savedTask.getId(), createdTaskEvent).get();

        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        log.info("topic: {}", result.getRecordMetadata().topic());
        log.info("partition: {}", result.getRecordMetadata().partition());
        log.info("offset: {}", result.getRecordMetadata().offset());

    }
}
