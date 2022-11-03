package com.kms.seft203.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJwtRepository extends JpaRepository<UserJwt,Long> {

    Optional<UserJwt> findByUserId(Long userId);

    Optional<UserJwt> findByRefreshToken(String refreshToken);

}
