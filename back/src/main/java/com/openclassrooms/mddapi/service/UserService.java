package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    UserDto save(UserDto userDto);
    UserDto findByUsername(String username);
    UserDto findByEmail(String email);
    List<UserDto> findAll();
}
