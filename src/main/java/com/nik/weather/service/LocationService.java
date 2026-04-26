package com.nik.weather.service;

import com.nik.weather.dao.LocationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LocationService {

    private final LocationDao locationDao;

    @Autowired
    private LocationService(LocationDao locationDao) {
        this.locationDao = locationDao;
    }

    @Transactional
}
