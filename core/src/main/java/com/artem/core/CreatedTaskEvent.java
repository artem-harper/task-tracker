package com.artem.core;


import lombok.*;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreatedTaskEvent {

    private String emailOwner;
    private String title;
    private String description;
    private LocalDateTime createdAt;

}
