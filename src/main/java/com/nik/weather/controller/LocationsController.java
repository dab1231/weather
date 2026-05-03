package com.nik.weather.controller;

import com.nik.weather.exception.SessionExpiredException;
import com.nik.weather.exception.SessionNotFoundException;
import com.nik.weather.service.LocationService;
import com.nik.weather.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/home")
public class LocationsController {

    private final SessionService sessionService;
    private final LocationService locationService;

    @Autowired
    public LocationsController(SessionService sessionService, LocationService locationService) {
        this.sessionService = sessionService;
        this.locationService = locationService;
    }

    @GetMapping
    public String showLocations(
            @CookieValue(value = "session_id", required = false) String sessionId,
            Model model) {

        try {
            if(sessionId == null) {
                return "redirect:/user/sign-in";
            }
            var session = sessionService.findById(UUID.fromString(sessionId));
            var user = session.getUser();
            var usersLocations = locationService.getUserLocations(user);
            model.addAttribute("locations", usersLocations);
        } catch (SessionExpiredException | SessionNotFoundException e) {
            return "redirect:/user/sign-in";
        }
        return "home";
    }
}
