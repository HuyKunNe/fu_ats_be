package com.fu.fuatsbe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fu.fuatsbe.entity.Skill;

@Repository
@Transactional
public interface SkillRepository extends JpaRepository<Skill, Integer> {

    Optional<Skill> findByName(String name);

    @Modifying
    @Query(value = "select distinct name from skill", nativeQuery = true)
    List<String> getDistinctByName();
}
