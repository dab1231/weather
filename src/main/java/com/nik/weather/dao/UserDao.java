package com.nik.weather.dao;

import com.nik.weather.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(user);

    }

    public Optional<User> findByLogin(String login) {
        Session session = sessionFactory.getCurrentSession();
        User maybeUser = session.createQuery("FROM User WHERE login = :login", User.class)
                .setParameter("login", login)
                .uniqueResult();
        return Optional.ofNullable(maybeUser);
    }

    public Optional<User> findById(Long id) {
        var currentSession = sessionFactory.getCurrentSession();
        var user = currentSession.find(User.class, id);
        return Optional.ofNullable(user);
    }
}
