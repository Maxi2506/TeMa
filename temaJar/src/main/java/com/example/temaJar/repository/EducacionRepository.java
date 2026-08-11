package com.example.temaJar.repository;

import com.example.temaJar.models.Educacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducacionRepository extends JpaRepository<Educacion, Long> {
}
