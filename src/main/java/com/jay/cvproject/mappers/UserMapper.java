package com.jay.cvproject.mappers;

import com.jay.cvproject.dtos.UserDto;
import com.jay.cvproject.models.auth.AuthUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    AuthUser dtoToEntity(UserDto dto);

    UserDto entityToDto(AuthUser entity);
}
