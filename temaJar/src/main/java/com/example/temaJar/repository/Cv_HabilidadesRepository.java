package com.example.temaJar.repository;

import com.example.temaJar.models.Cv_Habilidades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Cv_HabilidadesRepository extends JpaRepository<Cv_Habilidades, Long> {
}
