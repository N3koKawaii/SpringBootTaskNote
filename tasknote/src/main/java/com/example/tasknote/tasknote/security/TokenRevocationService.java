package com.example.tasknote.tasknote.security;

public interface TokenRevocationService {

    void revokeToken(String jti, String username, long millisecondsToLive);

    boolean isTokenRevoked(String jti, String username, long tokenIssuedAt);

    void revokeAllTokensForUser(String username);
}
