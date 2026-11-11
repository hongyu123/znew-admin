package com.hfw.service.auth;


import com.hfw.service.dto.LoginUser;

public interface AuthService {

    Token genToken(LoginUser loginUser) throws Exception;

    LoginUser verifyToken(String token) throws Exception;

    boolean delete(String token);

}
