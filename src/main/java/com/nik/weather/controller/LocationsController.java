package com.nik.weather.controller;

import com.nik.weather.dto.request.LocationReqDto;
import com.nik.weather.dto.response.CitiesRespDto;
import com.nik.weather.dto.response.WeatherRespDto;
import com.nik.weather.entity.Location;
import com.nik.weather.service.LocationService;
import com.nik.weather.service.SessionService;
import com.nik.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/home")
public class LocationsController {

    private final SessionService sessionService;
    private final LocationService locationService;
    private final WeatherService weatherService;

    @Autowired
    public LocationsController(SessionService sessionService, LocationService locationService, WeatherService weatherService) {
        this.sessionService = sessionService;
        this.locationService = locationService;
        this.weatherService = weatherService;
    }

    @GetMapping
    public String showLocations(
            @CookieValue(value = "session_id", required = false) String sessionId,
            Model model) {

        if (sessionId == null) {
            return "redirect:/user/sign-in";
        }
        var session = sessionService.findById(UUID.fromString(sessionId));
        var user = session.getUser();
        model.addAttribute("username", user.getLogin());
        var usersLocations = locationService.getUserLocations(user);

        var locationsWithWeather = new java.util.LinkedHashMap<Location, WeatherRespDto>();
        for (Location location : usersLocations) {
            var weather = weatherService.getWeather(location.getLatitude(), location.getLongitude());
            locationsWithWeather.put(location, weather);
        }
        model.addAttribute("locations", locationsWithWeather);

        return "home";
    }

    @GetMapping("/search")
    public String searchLocations(
            @CookieValue(value = "session_id", required = false) String sessionId,
            @RequestParam(required = false) String cityName,
            Model model) {

        if (cityName == null || cityName.isBlank()) {
            return "redirect:/home";
        }

        if (sessionId == null) {
            return "redirect:/user/sign-in";
        }
        var cities = weatherService.findCity(cityName);
        model.addAttribute("citiesDto", cities);
        return "search-results";
    }

    @PostMapping("/search")
    public String addLocation(
            @CookieValue(value = "session_id", required = false) String sessionId,
            @ModelAttribute LocationReqDto locationReqDto) {

        if (sessionId == null) {
            return "redirect:/user/sign-in";
        }

        var citiesRespDtos = weatherService.findCity(locationReqDto.name());
        var name = locationReqDto.name();
        var latitude = locationReqDto.latitude();
        var longitude = locationReqDto.longitude();

        for (CitiesRespDto citiesRespDto : citiesRespDtos) {
            if (Objects.equals(name, citiesRespDto.getName()) &&
                    Objects.equals(latitude, citiesRespDto.getLat()) &&
                    Objects.equals(longitude, citiesRespDto.getLon())) {

                var session = sessionService.findById(UUID.fromString(sessionId));
                var user = session.getUser();
                locationService.addLocation(user, locationReqDto);

            }
        }

        return "redirect:/home";

    }

    @PostMapping("/delete")
    public String deleteLocation(
            @CookieValue(value = "session_id", required = false) String sessionId,
            @RequestParam String cityName) {

        if (sessionId == null) {
            return "redirect:/user/sign-in";
        }

        var session = sessionService.findById(UUID.fromString(sessionId));
        var user = session.getUser();
        var userLocation = locationService.getUserLocation(user, cityName);
        locationService.deleteLocation(userLocation.getId(), user);
        return "redirect:/home";

    }
}
