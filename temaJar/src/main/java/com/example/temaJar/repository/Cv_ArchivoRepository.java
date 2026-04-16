package com.example.temaJar.repository;

import com.example.temaJar.models.Cv_Archivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface Cv_ArchivoRepository extends JpaRepository<Cv_Archivo, Long> {

    // Borrá el método findByCvBaseId y reemplazalo por este:
    List<Cv_Archivo> findByUsuarioId(Long usuarioId);

    // Si tenías algún otro método con "CvBase", cambialo por "Usuario"
}