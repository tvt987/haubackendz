package com.onlinemobilestore.controller;

import com.onlinemobilestore.constant.AccountMss;
import com.onlinemobilestore.dto.UserRegister;
import com.onlinemobilestore.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AccountController {
    @GetMapping("/homepage")
    public String getHomePage(){
        return "user/homepage";
    }
    @GetMapping("/loginpage")
    public String loginPage() {
        return "user/login";
    }
    @GetMapping("/loginpageerr")
    public String loginPageErr(Model model){
        model.addAttribute("loginerr",AccountMss.ERREMAILORPWR);
        return "user/login";
    }
    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user",new UserRegister());
        return "user/register";
    }
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String createAccount(Model model, @ModelAttribute("user") UserRegister user,
                                @RequestParam("passwordvali") String passwordvali) {

        if(!user.getPassword().equals(passwordvali)){
            model.addAttribute("errpass","Vui lòng xác nhận lại mật khẩu");
            return "user/register";
        }

        return "redirect:/homepage";
    }

}
