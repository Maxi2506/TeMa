package com.example.temaJar.servicios;

import com.example.temaJar.dtos.UsuarioDTO;
import com.example.temaJar.enumeracion.Rol;
import com.example.temaJar.models.*;
import com.example.temaJar.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LocalidadRepository localidadRepository;

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 1. MODO SEGURIDAD: Carga de usuario para el Login
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("No se encontró el usuario con el correo: " + correo));

        List<GrantedAuthority> permisos = new ArrayList<>();
        permisos.add(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString()));

        return new User(usuario.getCorreo(), usuario.getClave(), permisos);
    }

    // 2. MÉTODOS CRUD

    @Transactional(readOnly = true)
    public List<Usuario> obtenerTodo() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Usuario crearDesdeDTO(UsuarioDTO dto) {
        Usuario usuario = new Usuario();

        // Mapeo de datos simples desde el DTO
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setEdad(dto.getEdad());
        usuario.setCorreo(dto.getCorreo());
        usuario.setTelefono(dto.getTelefono());
        usuario.setDomicilio(dto.getDomicilio());

        // Conversión segura de String a Enum
        usuario.setRol(Rol.valueOf(dto.getRol().toUpperCase()));

        // Búsqueda de objetos relacionados por Nombre
        usuario.setLocalidad(localidadRepository.findByNombre(dto.getLocalidad())
                .orElseThrow(() -> new RuntimeException("Localidad no encontrada: " + dto.getLocalidad())));

        usuario.setProvincia(provinciaRepository.findByNombre(dto.getProvincia())
                .orElseThrow(() -> new RuntimeException("Provincia no encontrada: " + dto.getProvincia())));

        usuario.setPais(paisRepository.findByNombre(dto.getPais())
                .orElseThrow(() -> new RuntimeException("País no encontrado: " + dto.getPais())));

        // ENCRIPTACIÓN: Usamos el campo 'clave' que viene del DTO
        if (dto.getClave() == null || dto.getClave().isEmpty()) {
            throw new RuntimeException("La clave no puede estar vacía");
        }
        usuario.setClave(passwordEncoder.encode(dto.getClave()));

        return usuarioRepository.save(usuario);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Usuario actualizarDesdeDTO(Long id, UsuarioDTO dto) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNombre(dto.getNombre());
            usuario.setApellido(dto.getApellido());
            usuario.setCorreo(dto.getCorreo());
            usuario.setEdad(dto.getEdad());
            usuario.setTelefono(dto.getTelefono());
            usuario.setDomicilio(dto.getDomicilio());
            usuario.setRol(Rol.valueOf(dto.getRol().toUpperCase()));

            // Solo actualiza la clave si se envía una nueva en el DTO
            if (dto.getClave() != null && !dto.getClave().isEmpty()) {
                usuario.setClave(passwordEncoder.encode(dto.getClave()));
            }

            // Actualización de relaciones (si no se encuentra el nombre, mantiene la actual)
            usuario.setLocalidad(localidadRepository.findByNombre(dto.getLocalidad())
                    .orElse(usuario.getLocalidad()));
            usuario.setProvincia(provinciaRepository.findByNombre(dto.getProvincia())
                    .orElse(usuario.getProvincia()));
            usuario.setPais(paisRepository.findByNombre(dto.getPais())
                    .orElse(usuario.getPais()));

            return usuarioRepository.save(usuario);
        }).orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public boolean eliminar(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }
}