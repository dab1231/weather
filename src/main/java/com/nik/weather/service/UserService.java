package com.nik.weather.service;

import com.nik.weather.dao.UserDao;
import com.nik.weather.dto.request.UserReqDto;
import com.nik.weather.dto.response.SessionRespDto;
import com.nik.weather.entity.User;
import com.nik.weather.exception.InvalidLoginException;
import com.nik.weather.exception.InvalidPasswordException;
import com.nik.weather.exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserDao userDao;
    private final BCryptPasswordEncoder passwordEncoder;
    private final SessionService sessionService;

    @Autowired
    public UserService(UserDao userDao, BCryptPasswordEncoder passwordEncoder, SessionService sessionService) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.sessionService = sessionService;
    }

    @Transactional
    public void registration(UserReqDto userRegistrationReqDto) {
        var maybeUser = userDao.findByLogin(userRegistrationReqDto.login());

        if (maybeUser.isPresent()) {
            throw new UserAlreadyExistsException();
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

    @Transactional
    public SessionRespDto login(UserReqDto userReqDto) {
        var maybeUser = userDao.findByLogin(userReqDto.login());
        if(maybeUser.isPresent()) {
            User userFromDB = maybeUser.get();
            boolean match = passwordEncoder.matches(userReqDto.password(), userFromDB.getPassword());

            if(match) {
                var session = sessionService.createSession(userFromDB);
                return new SessionRespDto(session.getId(), session.getExpiresAt());
            }
            else{
                throw new InvalidPasswordException();
            }
        }
        else {
            throw new InvalidLoginException();
        }
    }
}
