package com.nik.weather.controller;

import com.nik.weather.exception.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LocationNotFoundException.class)
    public String handleLocationNotFoundException(Model model) {
        model.addAttribute("message", "Location not found, choose another location");
        return "error";
    }

    @ExceptionHandler(LocationAlreadyExistsException.class)
    public String handleLocationAlreadyExistsException(Model model) {
        model.addAttribute("message", "Location already exists, choose another location");
        return "error";
    }

    @ExceptionHandler(SessionExpiredException.class)
    public String handleSessionExpiredException() {
        return "redirect:/user/sign-in";
    }

    @ExceptionHandler(SessionNotFoundException.class)
    public String handleSessionNotFoundException() {
        return "redirect:/user/sign-in";
    }

    @ExceptionHandler(UnauthorizedAccessDeniedException.class)
    public String handleUnauthorizedAccessDeniedException(Model model) {
        model.addAttribute("message", "Unauthorized access. You do not have permission to perform this action.");
        return "error";
    }

    @ExceptionHandler(WeatherApiException.class)
    public String handleWeatherApiException(Model model) {
        model.addAttribute("message", "Error with weather api");
        return "error";
    }

    @ExceptionHandler({InvalidPasswordException.class, InvalidLoginException.class})
    public String handleInvalidCredentialsException(Model model) {
        model.addAttribute("error", "Invalid login or password, try again");
        return "sign-in";
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public String handleUserAlreadyExistsException(Model model) {
        model.addAttribute("error", "User already exists, choose another login");
        return "sign-up";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException ex) {
        if (ex.getMessage() != null && ex.getMessage().contains("Invalid UUID string")) {
            return "redirect:/user/sign-in";
        }
        return "redirect:/home";
    }


}
