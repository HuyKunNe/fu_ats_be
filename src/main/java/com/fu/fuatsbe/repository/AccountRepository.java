package com.fu.fuatsbe.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fu.fuatsbe.entity.Account;
import com.fu.fuatsbe.entity.Role;

import java.util.Optional;

@Repository
@Transactional
public interface AccountRepository extends JpaRepository<Account, Integer> {
    public Optional<Account> findAccountByEmail(String email);

    public boolean existsByRole(Role role);

}
