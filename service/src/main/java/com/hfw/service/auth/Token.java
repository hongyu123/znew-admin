package com.hfw.service.auth;

import java.time.LocalDateTime;

public record Token(String uuid, String token, LocalDateTime expireTime) {
}
