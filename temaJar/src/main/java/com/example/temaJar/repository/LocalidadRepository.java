package com.example.temaJar.repository;

import com.example.temaJar.models.Localidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocalidadRepository extends JpaRepository<Localidad, Long> {
    Optional<Localidad> findByNombre(String nombre);
}
