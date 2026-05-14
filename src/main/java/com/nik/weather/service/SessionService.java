package com.nik.weather.service;

import com.nik.weather.dao.SessionDao;
import com.nik.weather.dto.request.SessionReqDto;
import com.nik.weather.entity.SessionEntity;
import com.nik.weather.entity.User;
import com.nik.weather.exception.SessionExpiredException;
import com.nik.weather.exception.SessionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SessionService {

    private final SessionDao sessionDao;

    @Value("${session.duration.hours:24}")
    private int sessionDurationHours;

    @Autowired
    public SessionService(SessionDao sessionDao) {
        this.sessionDao = sessionDao;
    }

    @Transactional
    public SessionEntity createSession(User user) {
        var session = SessionEntity.builder()
                .user(user)
                .expiresAt(LocalDateTime.now().plusHours(sessionDurationHours))
                .build();
        sessionDao.createSession(session);
        return session;
    }

    @Transactional
    public SessionEntity findById(UUID id) {

        var maybeSession = sessionDao.findById(id);
        if (maybeSession.isPresent()) {
            var sessionEntity = maybeSession.get();
            if(sessionEntity.getExpiresAt().isAfter(LocalDateTime.now())) {
                return sessionEntity;

            }
            else {
                sessionDao.deleteById(sessionEntity.getId());
                throw new SessionExpiredException();
            }
        }
        else {
            throw new SessionNotFoundException();
        }
    }

    @Transactional
    public void deleteSession(SessionReqDto sessionReqDto) {
        sessionDao.deleteById(sessionReqDto.id());
    }
}
