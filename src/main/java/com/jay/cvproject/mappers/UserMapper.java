package com.jay.cvproject.mappers;

import com.jay.cvproject.dtos.UserDto;
import com.jay.cvproject.models.auth.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User dtoToEntity(UserDto dto);

    UserDto entityToDto(User entity);
}
