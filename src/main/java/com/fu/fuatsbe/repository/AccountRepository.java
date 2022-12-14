package com.fu.fuatsbe.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fu.fuatsbe.entity.Account;
import com.fu.fuatsbe.entity.Role;

@Repository
@Transactional
public interface AccountRepository extends JpaRepository<Account, Integer> {

    public Optional<Account> findAccountByEmail(String email);

    public Account findByEmail(String email);

    public boolean existsByRole(Role role);

    public Page<Account> findByRole(Role role, Pageable pageable);

    public Page<Account> findByStatus(String status, Pageable pageable);

    @Query(nativeQuery = true, value = "select * from account where role_id != 3")
    Page<Account> getEmployeeAccount(Pageable pageable);
    @Query(nativeQuery = true, value = "select notification_token from account where email like ?1 limit 1")
    String getNotiTokenByMail(String mail);

}
