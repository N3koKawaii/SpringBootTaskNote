package com.example.tasknote.tasknote.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RevokedTokenRepository extends JpaRepository<RevokedToken, String> {

    boolean existsByJti(String jti);
    boolean existsByJtiAndUsername(String jti, String username);
    @Modifying
    @Query("DELETE FROM RevokedToken rt WHERE rt.expiresAt <= :currentTime")
    void deleteExpired(long currentTime);

}
