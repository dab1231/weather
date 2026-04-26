package com.nik.weather.service;

import com.nik.weather.dao.SessionDao;
import com.nik.weather.dto.request.SessionReqDto;
import com.nik.weather.dto.response.SessionRespDto;
import com.nik.weather.entity.SessionEntity;
import com.nik.weather.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

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

    @Transactional
    public SessionRespDto findById(SessionReqDto sessionReqDto) {

        var maybeSession = sessionDao.findById(sessionReqDto.id());
        if (maybeSession.isPresent()) {
            var sessionEntity = maybeSession.get();
            if(sessionEntity.getExpiresAt().isAfter(LocalDateTime.now())) {
                return new SessionRespDto(
                        sessionEntity.getId(),
                        sessionEntity.getExpiresAt()
                );

            }
            else {
                sessionDao.deleteById(sessionEntity.getId());
                //todo ошибка сессия истекла
            }
        }
        else {
            //todo ошибка сессии не существует
        }
    }

    @Transactional
    public void deleteSession(SessionReqDto sessionReqDto) {
        sessionDao.deleteById(sessionReqDto.id());
    }
}
