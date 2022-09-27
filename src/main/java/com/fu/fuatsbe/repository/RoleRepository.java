package com.fu.fuatsbe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fu.fuatsbe.entity.Role;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Role, Integer> {
    List<Role> findAll();

    List<Role> findByStatus(int status);

    @Modifying
    @Query("update Role set status = 1 where id = ?1")
    int deleteRoleById(int id);
}
