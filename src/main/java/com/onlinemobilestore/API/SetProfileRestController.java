package com.onlinemobilestore.API;

import com.onlinemobilestore.entity.User;
import com.onlinemobilestore.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
@RestController

public class SetProfileRestController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    UserRepository userDAO;

    @PostMapping("/api/set-profile/{userId}/{fullName}/{phoneNumber}/{address}/{birthDay}/{email}")
    public UserProfileResponse setProfile(   @PathVariable Integer userId,
                                             @PathVariable String fullName,
                                             @PathVariable String phoneNumber,
                                             @PathVariable String address,
                                             @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDay,
                                             @PathVariable String email) {
        Optional<User> optionalUser = userDAO.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFullName(fullName);
            user.setPhoneNumber(phoneNumber);
            user.setAddress(address);
//            user.setBirthDay(birthDay);
            user.setEmail(email);
            user.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
            user.setActive(true);
            userDAO.save(user);

            // Tạo đối tượng UserProfileResponse và set thông tin
            UserProfileResponse userProfileResponse = new UserProfileResponse(
                    user.getId(),
                    user.getFullName(),
                    user.getPhoneNumber(),
                    user.getAddress(),
//                    user.getBirthDay(),
                    user.getEmail()
            );

            return userProfileResponse;
        }
        return null;
    }

    @Data
    @AllArgsConstructor
    public class UserProfileResponse {
        private Integer userId;
        private String fullName;
        private String phoneNumber;
        private String address;
        private String email;
    }
}
