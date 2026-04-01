package com.example.temaJar.repository;

import com.example.temaJar.models.Experiencia;
import com.example.temaJar.models.Puesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExperienciaRepository extends JpaRepository<Experiencia, Long> {

}