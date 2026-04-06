package com.example.temaJar.repository;

import com.example.temaJar.models.Experiencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; // <--- Importante

@Repository
public interface ExperienciaRepository extends JpaRepository<Experiencia, Long> {
}