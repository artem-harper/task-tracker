package com.artem.taskapi.dto;

import com.artem.taskapi.entity.TaskStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RespTaskDto {

    private Long id;
    private String title;
    private String description;
    private TaskStatus status;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private LocalDateTime done_at;

}
