package com.nik.weather.service;

import com.nik.weather.dao.LocationDao;
import com.nik.weather.dto.request.LocationReqDto;
import com.nik.weather.entity.Location;
import com.nik.weather.entity.User;
import com.nik.weather.exception.LocationNotFoundException;
import com.nik.weather.exception.UnauthorizedAccessDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LocationService {

    private final LocationDao locationDao;

    @Autowired
    public LocationService(LocationDao locationDao) {
        this.locationDao = locationDao;
    }

    @Transactional
    public void addLocation(User user, LocationReqDto locationReqDto) {
        var location = Location.builder()
                .name(locationReqDto.name())
                .user(user)
                .latitude(locationReqDto.latitude())
                .longitude(locationReqDto.longitude())
                .build();
        locationDao.save(location);
    }

    @Transactional
    public void deleteLocation(Long locationId, User user) {
        var locationFromDb = locationDao.findById(locationId);
        if(locationFromDb.isPresent()) {
            var location = locationFromDb.get();
            if (location.getUser().getId().equals(user.getId())) {
                locationDao.delete(location.getId());
            }
            else {
                throw new UnauthorizedAccessDeniedException();
            }
        }
        else {
            throw new LocationNotFoundException();
        }
    }

    @Transactional
    public List<Location> getUserLocations(User user) {
        return locationDao.findByUser(user);
    }

    @Transactional
    public Location getUserLocation(User user, String cityName) {
        return locationDao.findByUserAndName(user, cityName);
    }
}
