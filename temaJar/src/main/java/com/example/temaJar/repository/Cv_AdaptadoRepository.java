package com.example.temaJar.repository;

import com.example.temaJar.models.Cv_Adaptado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Cv_AdaptadoRepository extends JpaRepository<Cv_Adaptado, Long> {
}
