package com.nik.weather.controller;

import com.nik.weather.dto.request.UserReqDto;
import com.nik.weather.exception.InvalidLoginException;
import com.nik.weather.exception.InvalidPasswordException;
import com.nik.weather.exception.UserAlreadyExistsException;
import com.nik.weather.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
    public UserController(UserService userService) {
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
            return "redirect:/user/sign-in";
        }
        catch (UserAlreadyExistsException e) {
            model.addAttribute("error", "User already exists");
            return "sign-up";
        }
    }

    @GetMapping("/sign-in")
    public String signInPage() {
        return "sign-in";
    }

    @PostMapping("/sign-in")
    public String signIn(
            @RequestParam String login,
            @RequestParam String password,
            HttpServletResponse httpServletResponse,
            Model model) {

        try {
            UserReqDto userReqDto = new UserReqDto(login, password);
            var sessionRespDto = userService.login(userReqDto);
            var cookie = new Cookie("session_id", sessionRespDto.id().toString());
            cookie.setMaxAge(24 * 60 * 60);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            httpServletResponse.addCookie(cookie);
            return "redirect:/home";
        } catch (InvalidPasswordException e) {
            model.addAttribute("error", "Invalid password");
            return "sign-in";
        } catch (InvalidLoginException e) {
            model.addAttribute("error", "Invalid username");
            return "sign-in";
        }
    }
}
