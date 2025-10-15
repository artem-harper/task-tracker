package com.artem.taskapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;


@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserReqDto {

    @Email(message="Please provide a valid email address")
    @Pattern(regexp=".+@.+\\..+", message="Please provide a valid email address")
    private String email;
    private String password;

}
