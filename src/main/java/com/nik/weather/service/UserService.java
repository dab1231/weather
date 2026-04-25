package com.nik.weather.service;

import com.nik.weather.dao.UserDao;
import com.nik.weather.dto.request.UserRegistrationReqDto;
import com.nik.weather.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserService {

    private final UserDao userDao;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserService(UserDao userDao, BCryptPasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registration(UserRegistrationReqDto userRegistrationReqDto) {
        var maybeUser = userDao.findByLogin(userRegistrationReqDto.login());

        if (maybeUser.isPresent()) {
            //todo создать кастомную ошибку (user already exists) и бросить
        }

        String password = userRegistrationReqDto.password();
        String login = userRegistrationReqDto.login();
        String hashPassword = passwordEncoder.encode(password);

        var user = User.builder()
                .login(login)
                .password(hashPassword)
                .build();
        userDao.save(user);
    }


}
