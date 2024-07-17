package com.appswave.task.model.dtos;

import com.appswave.task.model.entity.BaseEntity;
import com.appswave.task.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO extends BaseEntity {
    private String fullName;
    private String email;
    private String password;
    private LocalDate dateOfBirth;
    private UserRole role;
}

