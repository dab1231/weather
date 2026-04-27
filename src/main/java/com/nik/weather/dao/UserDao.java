package com.nik.weather.dao;

import com.nik.weather.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class UserDao {

    private final SessionFactory sessionFactory;

    @Autowired
    private UserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public void save(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(user);

    }

    @Transactional
    public Optional<User> findByLogin(String login) {
        Session session = sessionFactory.getCurrentSession();
        User maybeUser = session.createQuery("FROM User WHERE login = :login", User.class)
                .setParameter("login", login)
                .uniqueResult();
        return Optional.ofNullable(maybeUser);
    }

    @Transactional
    public Optional<User> findById(Long id) {
        var currentSession = sessionFactory.getCurrentSession();
        var user = currentSession.find(User.class, id);
        return Optional.ofNullable(user);
    }
}
