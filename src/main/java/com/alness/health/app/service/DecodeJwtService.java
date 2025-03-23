package com.alness.health.app.service;

import com.alness.health.app.dto.JwtDto;

public interface DecodeJwtService {
    public JwtDto decodeJwt(String jwtToken);
    public Boolean isValidToken(String jwtToken);
}
