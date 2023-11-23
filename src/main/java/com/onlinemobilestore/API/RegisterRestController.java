package com.onlinemobilestore.API;

import com.onlinemobilestore.entity.User;
import com.onlinemobilestore.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/register")
public class RegisterRestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
        private PasswordEncoder passwordEncoder;
    @PostMapping("/{email}/{phoneNumber}/{fullName}/{address}/{birthDay}/{password}/{confirmPassword}")
    public String registerUser(
            @PathVariable String email,
            @PathVariable String phoneNumber,
            @PathVariable String fullName,
            @PathVariable String address,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date birthDay,
            @PathVariable String password,
            @PathVariable String confirmPassword) {
        try {
            System.out.println("Register");

            // Kiá»ƒm tra tÃ­nh há»£p lá»‡ cá»§a thÃ´ng tin Ä‘Äƒng kÃ½
            if (!password.equals(confirmPassword)) {
                return "Password and ConfirmPassword not match";
            }

            // Kiá»ƒm tra xem ngÆ°á»i dÃ¹ng cÃ³ tá»“n táº¡i khÃ´ng
            Optional<User> optionalUser = userRepository.findByEmail(email);
            if (optionalUser.isPresent()) {
                return "User with this email already exists";
            }
            if (userRepository.existsUserByPhoneNumber(phoneNumber)) {
                return "User with this phoneNumber already exists";
            }
            // NgÆ°á»i dÃ¹ng khÃ´ng tá»“n táº¡i, táº¡o má»›i ngÆ°á»i dÃ¹ng
            User user = new User();
            user.setEmail(email);
            user.setPhoneNumber(phoneNumber);
            user.setAddress(address);
            user.setBirthday(birthDay);
            user.setFullName(fullName);

            // Hash the password before saving it
            String hashedPassword = passwordEncoder.encode(password);
            user.setPassword(hashedPassword);

            user.setCreateDate(new Date());
            user.setActive(true);
            user.setRoles("USER");
            user.setAvatar(null);

            // LÆ°u thÃ´ng tin ngÆ°á»i dÃ¹ng vÃ o cÆ¡ sá»Ÿ dá»¯ liá»‡u
            userRepository.save(user);

            // Tráº£ vá» thÃ´ng bÃ¡o thÃ nh cÃ´ng
            return "Successfully";

        } catch (Exception e) {
            e.printStackTrace();
            // Xá»­ lÃ½ lá»—i vÃ  tráº£ vá» thÃ´ng bÃ¡o lá»—i
            return "Error during user registration";
        }
    }
}
