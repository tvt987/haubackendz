package com.onlinemobilestore.service;


import com.onlinemobilestore.dto.UserRegister;
import org.springframework.stereotype.Component;

@Component
public interface UserService {
    String add(UserRegister userDto);

}
