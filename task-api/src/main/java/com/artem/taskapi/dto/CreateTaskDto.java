package com.artem.taskapi.dto;

import com.artem.taskapi.entity.TaskStatus;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTaskDto {

    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private UserDto owner;

}
