package com.nik.weather.dao;


import com.nik.weather.entity.SessionEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class SessionDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public SessionDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void createSession(SessionEntity sessionEntity) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.persist(sessionEntity);
    }

    public Optional<SessionEntity> findById(UUID id) {
        Session currentSession = sessionFactory.getCurrentSession();
        SessionEntity sessionFromDB = currentSession.find(SessionEntity.class, id);
        return Optional.ofNullable(sessionFromDB);
    }

    public void deleteById(UUID id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Optional<SessionEntity> maybeSession = findById(id);
        maybeSession.ifPresent(currentSession::remove);
    }
}
