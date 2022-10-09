package com.fu.fuatsbe.repository;

import com.fu.fuatsbe.entity.Account;
import com.fu.fuatsbe.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationRepository extends JpaRepository<VerificationToken,String> {
    Optional<VerificationToken> findByToken(String token);
    Optional<VerificationToken> findByAccount(Account account);

}
