package com.appswave.task.mapper;

import com.appswave.task.model.dtos.UserDTO;
import com.appswave.task.model.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);

    List<UserDTO> toListDTO(List<User> user);

    User toEntity(UserDTO user);
}
