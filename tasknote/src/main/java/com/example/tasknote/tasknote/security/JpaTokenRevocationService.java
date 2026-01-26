package com.example.tasknote.tasknote.security;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class JpaTokenRevocationService implements TokenRevocationService {

    private static final String GLOBAL_PREFIX = "GLOBAL_";

    private final RevokedTokenRepository revokedTokenRepository;

    public JpaTokenRevocationService(RevokedTokenRepository revokedTokenRepository) {
        this.revokedTokenRepository = revokedTokenRepository;
    }

    @Override
    @Transactional
    public void revokeToken(String jti, String username, long millisecondsToLive) { 
        if (jti == null || jti.isBlank()) return;

        RevokedToken token = new RevokedToken();
        token.setJti(jti);
        token.setUsername(username);
        token.setExpiresAt(System.currentTimeMillis() + millisecondsToLive);
        

        revokedTokenRepository.save(token);
    }

    @Override
    public boolean isTokenRevoked(String jti, String username, long tokenIssuedAt) {
        if (jti == null || username == null) return false;

        if (revokedTokenRepository.existsById(jti)) {
            return true;
        }

        return revokedTokenRepository.findById(GLOBAL_PREFIX + username)
            .map(RevokedToken::getExpiresAt)
            .filter(expiresAt -> tokenIssuedAt < expiresAt)
            .isPresent();
    }

    @Override
    public void revokeAllTokensForUser(String username) {
        if (username== null || username.isBlank()) return;

        RevokedToken globalToken = new RevokedToken();

        globalToken.setJti(GLOBAL_PREFIX + username);
        globalToken.setUsername(username);
        globalToken.setExpiresAt(System.currentTimeMillis());
        
        revokedTokenRepository.save(globalToken);
    }

    @Scheduled(fixedDelay = 3600000) // every hour
    @Transactional
    public void cleanup() {
        revokedTokenRepository.deleteExpired(System.currentTimeMillis());
    }

}
