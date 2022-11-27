package com.fu.fuatsbe.repository;

import com.fu.fuatsbe.entity.Department;
import com.fu.fuatsbe.entity.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface PositionRepository extends JpaRepository<Position, Integer> {
    Optional<Position> findPositionByName(String name);

    Optional<Position> findById(int id);

    Page<Position> findByDepartment(Department department, Pageable pageable);

    Page<Position> findByNameContaining(String name, Pageable pageable);

    @Query(nativeQuery = true, value = "select name from position")
    List<String> getAllPositionName();
    @Query(nativeQuery = true, value = "select id,name from position")
    List<Tuple> getPositionIdAndName();
}
