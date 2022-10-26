package com.fu.fuatsbe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fu.fuatsbe.entity.Skill;

@Repository
@Transactional
public interface SkillRepository extends JpaRepository<Skill, Integer> {

    Optional<Skill> findByName(String name);
}
