package com.onlinemobilestore.service.serviceImpl;

import com.onlinemobilestore.dto.UserRegister;
import com.onlinemobilestore.entity.User;
import com.onlinemobilestore.repository.UserRepository;
import com.onlinemobilestore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class UserServiceImpl implements UserService{
    @Autowired
     private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public String add(UserRegister userDto) {
        if(userRepository.existsByEmail(userDto.getEmail())){
            return "exist email";
        }else if(userRepository.existsUserByPhoneNumber(userDto.getPhoneNumber())){
            return "exist phoneNumber";
        }
        String encodedPassword = this.passwordEncoder.encode(userDto.getPassword());
        User user = new User(userDto);
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return  "add " + userDto.getFullName() ;
    }
}
