package com.hfw.service.auth;

import com.hfw.service.dto.LoginUser;

public interface AuthService {

    Token genToken(LoginUser loginUser) throws Exception;

    LoginUser verifyToken(String token);

    void updateToken(String token, LoginUser loginUser);

    boolean delete(String token);

    boolean exists(String token);

}
