package com.kms.seft203.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

//    User findOne(String id);

    @Query(value = "SELECT u from User u where u.verificationcode = ?1")
    User findByVerificationcode(String code);
}
