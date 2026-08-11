package com.example.temaJar.repository;

import com.example.temaJar.models.Provincia;
import com.example.temaJar.models.Pais;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProvinciaRepository extends JpaRepository<Provincia, Long> {

    Optional<Provincia> findByNombre(String nombre);
    Optional<Provincia> findByNombreAndPais(String nombre, Pais pais);
    Optional<Provincia> findFirstByNombreAndPais(String nombre, Pais pais);

}
