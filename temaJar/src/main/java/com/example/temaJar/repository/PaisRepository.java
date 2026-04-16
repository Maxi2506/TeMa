package com.example.temaJar.repository;

import com.example.temaJar.models.Pais;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaisRepository extends JpaRepository<Pais, Long> {
    Optional<Pais> findByNombre(String nombre);
    Optional<Pais> findFirstByNombre(String nombre);
}
