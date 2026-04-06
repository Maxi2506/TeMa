package com.example.temaJar.repository;

import com.example.temaJar.models.Habilidades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HabilidadesRepository extends JpaRepository<Habilidades, Long> {

    Optional<Habilidades> findByNombre(String nombre);

}
