package com.example.temaJar.repository;

import com.example.temaJar.models.Cv_Archivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Cv_ArchivoRepository extends JpaRepository<Cv_Archivo, Long> {
}
