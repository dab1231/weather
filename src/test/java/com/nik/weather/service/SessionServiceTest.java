package com.nik.weather.service;

import com.nik.weather.config.SpringConfig;
import com.nik.weather.dao.SessionDao;
import com.nik.weather.dto.request.UserReqDto;
import com.nik.weather.exception.SessionExpiredException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringJUnitConfig(SpringConfig.class)
public class SessionServiceTest {

    @Autowired
    private SessionService sessionService;
    @Autowired
    private SessionDao sessionDao;
    @Autowired
    private UserService userService;

    @Test
    void createSession() {
        var userRegistrationReqDto = new UserReqDto("dummy", "dummy");
        userService.registration(userRegistrationReqDto);
        var sessionRespDto = userService.login(userRegistrationReqDto);
        var session = sessionDao.findById(sessionRespDto.id());

        assertThat(session).isPresent();
        session.ifPresent(sessionEntity -> assertThat(sessionEntity.getId()).isEqualTo(sessionRespDto.id()));
    }

    @Test
    void throwExceptionIfSessionWasExpired() {
        var userRegistrationReqDto = new UserReqDto("dummy", "dummy");
        userService.registration(userRegistrationReqDto);
        var sessionRespDto = userService.login(userRegistrationReqDto);

        assertThrows(SessionExpiredException.class,
                () -> sessionService.findById(sessionRespDto.id()));
    }
}