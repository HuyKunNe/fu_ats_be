package com.fu.fuatsbe.repository;

import javax.annotation.Nullable;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fu.fuatsbe.entity.Account;
import com.fu.fuatsbe.entity.Role;

import java.util.Optional;

@Repository
@Transactional
public interface AccountRepository extends JpaRepository<Account, Integer> {

    public Optional<Account> findAccountByEmail(String email);

    public boolean existsByRole(Role role);

    public Page<Account> findByRole(Role role, Pageable pageable);

    public Page<Account> findByStatus(String status, Pageable pageable);
    @Query(nativeQuery = true, value = "select * from account where role_id = 3")
    Page<Account> getCandidateAccount(Pageable pageable);

    @Query(nativeQuery = true, value = "select * from account where role_id != 3 and status like 'ACTIVATE'")
    Page<Account> getEmployeeAccount(Pageable pageable);

}
