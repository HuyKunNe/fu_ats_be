package com.fu.fuatsbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fu.fuatsbe.entity.Role;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
