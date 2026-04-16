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
import java.util.stream.Collectors;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private LocalidadRepository localidadRepository;
    @Autowired private ProvinciaRepository provinciaRepository;
    @Autowired private PaisRepository paisRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("No se encontró el usuario: " + correo));

        List<GrantedAuthority> permisos = new ArrayList<>();
        permisos.add(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString()));

        return new User(usuario.getCorreo(), usuario.getClave(), permisos);
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> obtenerTodo() {
        return usuarioRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UsuarioDTO obtenerPorId(Long id) {
        return usuarioRepository.findById(id)
                .map(this::convertirADTO)
                .orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public UsuarioDTO crear(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setEdad(dto.getEdad());
        usuario.setCorreo(dto.getCorreo());
        usuario.setTelefono(dto.getTelefono());
        usuario.setDomicilio(dto.getDomicilio());
        usuario.setClave(passwordEncoder.encode(dto.getClave()));
        usuario.setRol(Rol.USUARIO);

        // 1. Buscamos los objetos geográficos
        Pais p = paisRepository.findByNombre(dto.getPais())
                .orElseThrow(() -> new RuntimeException("País no encontrado: " + dto.getPais()));

        Provincia prov = provinciaRepository.findByNombre(dto.getProvincia())
                .orElseThrow(() -> new RuntimeException("Provincia no encontrada: " + dto.getProvincia()));

        Localidad loc = localidadRepository.findByNombre(dto.getLocalidad())
                .orElseThrow(() -> new RuntimeException("Localidad no encontrada: " + dto.getLocalidad()));

        // 2. ASIGNACIÓN EXPLÍCITA (Crucial para que no vuelvan null)
        usuario.setPais(p);
        usuario.setProvincia(prov);
        usuario.setLocalidad(loc);

        Usuario guardado = usuarioRepository.save(usuario);
        return convertirADTO(guardado);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public UsuarioDTO modificar(Long id, UsuarioDTO dto) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNombre(dto.getNombre());
            usuario.setApellido(dto.getApellido());
            usuario.setCorreo(dto.getCorreo());
            usuario.setEdad(dto.getEdad());
            usuario.setTelefono(dto.getTelefono());
            usuario.setDomicilio(dto.getDomicilio());

            if (dto.getRol() != null) {
                usuario.setRol(Rol.valueOf(dto.getRol().toUpperCase()));
            }

            if (dto.getClave() != null && !dto.getClave().isEmpty()) {
                usuario.setClave(passwordEncoder.encode(dto.getClave()));
            }

            // Actualización de relaciones geográficas
            if (dto.getPais() != null) {
                usuario.setPais(paisRepository.findByNombre(dto.getPais())
                        .orElse(usuario.getPais()));
            }
            if (dto.getProvincia() != null) {
                usuario.setProvincia(provinciaRepository.findByNombre(dto.getProvincia())
                        .orElse(usuario.getProvincia()));
            }
            if (dto.getLocalidad() != null) {
                usuario.setLocalidad(localidadRepository.findByNombre(dto.getLocalidad())
                        .orElse(usuario.getLocalidad()));
            }

            Usuario actualizado = usuarioRepository.save(usuario);
            return convertirADTO(actualizado);
        }).orElseThrow(() -> new RuntimeException("No se encontró el usuario con ID: " + id));
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public boolean eliminar(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private UsuarioDTO convertirADTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setCorreo(usuario.getCorreo());
        dto.setEdad(usuario.getEdad());
        dto.setTelefono(usuario.getTelefono());
        dto.setDomicilio(usuario.getDomicilio());
        dto.setRol(usuario.getRol().toString());

        // Mapeamos los nombres desde los objetos relacionados
        if (usuario.getPais() != null) dto.setPais(usuario.getPais().getNombre());
        if (usuario.getProvincia() != null) dto.setProvincia(usuario.getProvincia().getNombre());
        if (usuario.getLocalidad() != null) dto.setLocalidad(usuario.getLocalidad().getNombre());

        return dto;
    }
}