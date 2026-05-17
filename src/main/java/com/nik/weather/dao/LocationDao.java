package com.nik.weather.dao;

import com.nik.weather.entity.Location;
import com.nik.weather.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LocationDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public LocationDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Location location) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.persist(location);
    }

    public void delete(Long id) {
        Session currentSession = sessionFactory.getCurrentSession();
        var location = currentSession.find(Location.class, id);
        currentSession.remove(location);
    }

    public List<Location> findByUser(User user) {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.createQuery("FROM Location WHERE user = :user", Location.class)
                .setParameter("user", user)
                .getResultList();
    }

    public Optional<Location> findById(Long id) {
        Session currentSession = sessionFactory.getCurrentSession();
        return Optional.ofNullable(currentSession.find(Location.class, id));
    }

    public Optional<Location> findByUserAndName(User user, String cityName) {
        var currentSession = sessionFactory.getCurrentSession();
        return Optional.ofNullable(currentSession.createQuery("FROM Location WHERE user = :user AND name = :cityName", Location.class)
                .setParameter("user", user)
                .setParameter("cityName", cityName)
                .uniqueResult());
    }
}
