package com.artem.taskapi.dto;

import lombok.*;


@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserReqDto {

    private String email;
    private String password;

}
