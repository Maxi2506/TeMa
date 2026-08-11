package com.example.temaJar.repository;

import com.example.temaJar.models.Puesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PuestoRepository extends JpaRepository<Puesto, Long> {

    Optional<Puesto> findByNombre(String nombre);
    List<Puesto> findByCategoria(String categoria);
    List<Puesto> findByNombreContainingIgnoreCase(String nombre);
}
