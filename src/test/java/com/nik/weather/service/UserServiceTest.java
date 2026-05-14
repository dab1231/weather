package com.nik.weather.service;

import com.nik.weather.config.SpringConfig;
import com.nik.weather.dao.UserDao;
import com.nik.weather.dto.request.UserReqDto;
import com.nik.weather.entity.User;
import com.nik.weather.exception.UserAlreadyExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringJUnitConfig(SpringConfig.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;


    @Test
    void userAddedInDbAfterRegistration() {
        userService.registration(new UserReqDto("dummy", "dummy"));
        var maybeUser = userDao.findByLogin("dummy");
        assertThat(maybeUser).isPresent();
        maybeUser.ifPresent(user -> assertThat(user).isInstanceOf(User.class));
    }

    @Test
    void throwExceptionIfUserAlreadyExists() {
        userService.registration(new UserReqDto("dummy", "dummy"));
        Assertions.assertThrows(UserAlreadyExistsException.class,
                () -> userService.registration(new UserReqDto("dummy", "dummy")));
    }
}
