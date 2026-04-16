package com.example.temaJar.repository;

import com.example.temaJar.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Cambiado de buscarPorEmail a findByCorreo para que coincida con el atributo de la Entidad
    // Usamos Optional para un manejo de errores más limpio
    Optional<Usuario> findByCorreo(String correo);
    Optional<Usuario> findFirstByCorreo(String correo);

}
