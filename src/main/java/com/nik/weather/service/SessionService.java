package com.nik.weather.service;

import com.nik.weather.dao.SessionDao;
import com.nik.weather.entity.SessionEntity;
import com.nik.weather.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class SessionService {

    private final SessionDao sessionDao;

    @Autowired
    private SessionService(SessionDao sessionDao) {
        this.sessionDao = sessionDao;
    }

    @Transactional
    public SessionEntity createSession(User user) {
        var session = SessionEntity.builder()
                .user(user)
                .expiresAt(LocalDateTime.now().plusHours(24))
                .build();
        sessionDao.createSession(session);
        return session;
    }
}
