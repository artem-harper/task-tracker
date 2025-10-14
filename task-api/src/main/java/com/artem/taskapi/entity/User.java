package com.artem.taskapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@ToString(exclude = "list")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

}
