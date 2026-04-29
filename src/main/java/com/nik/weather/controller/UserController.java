package com.nik.weather.controller;

import com.nik.weather.dto.request.UserReqDto;
import com.nik.weather.exception.UserAlreadyExistsException;
import com.nik.weather.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    private UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/sign-up")
    public String signUpPage() {
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String signUp(@RequestParam String login,
                         @RequestParam String password,
                         Model model) {

        UserReqDto userReqDto = new UserReqDto(login, password);
        try {
            userService.registration(userReqDto);
            return "redirect:/user/sing-in";
        }
        catch (UserAlreadyExistsException e) {
            model.addAttribute("error", "User already exists");
            return "sign-up";
        }

    }
}
