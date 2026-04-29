package com.nik.weather.dao;


import com.nik.weather.entity.SessionEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Component
public class SessionDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public SessionDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public void createSession(SessionEntity sessionEntity) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.persist(sessionEntity);
    }

    @Transactional
    public Optional<SessionEntity> findById(UUID id) {
        Session currentSession = sessionFactory.getCurrentSession();
        SessionEntity sessionFromDB = currentSession.find(SessionEntity.class, id);
        return Optional.ofNullable(sessionFromDB);
    }

    @Transactional
    public void deleteById(UUID id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Optional<SessionEntity> maybeSession = findById(id);
        maybeSession.ifPresent(currentSession::remove);
    }
}
