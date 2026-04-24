package com.nik.weather.dao;

import com.nik.weather.entity.Location;
import com.nik.weather.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class LocationDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public LocationDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public void save(Location location) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.persist(location);
    }

    @Transactional
    public void delete(Location location) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.remove(location);
    }

    @Transactional
    public List<Location> findByUser(User user) {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.createQuery("FROM Location WHERE user = :user", Location.class)
                .setParameter("user", user)
                .getResultList();
    }
}
