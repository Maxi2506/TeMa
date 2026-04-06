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

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("No se encontró el usuario con el correo: " + correo));

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

        // Mapeo básico
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setEdad(dto.getEdad());
        usuario.setCorreo(dto.getCorreo());
        usuario.setTelefono(dto.getTelefono());
        usuario.setDomicilio(dto.getDomicilio());
        usuario.setRol(Rol.valueOf(dto.getRol().toUpperCase()));

        usuario.setLocalidad(localidadRepository.findByNombre(dto.getLocalidad())
                .orElseThrow(() -> new RuntimeException("Error: La localidad '" + dto.getLocalidad() + "' no existe en la base de datos.")));

        usuario.setProvincia(provinciaRepository.findByNombre(dto.getProvincia())
                .orElseThrow(() -> new RuntimeException("Error: La provincia '" + dto.getProvincia() + "' no existe.")));

        usuario.setPais(paisRepository.findByNombre(dto.getPais())
                .orElseThrow(() -> new RuntimeException("Error: El país '" + dto.getPais() + "' no existe.")));

        // Encriptación de seguridad
        if (dto.getClave() == null || dto.getClave().isEmpty()) {
            throw new RuntimeException("La clave es obligatoria para el registro.");
        }
        usuario.setClave(passwordEncoder.encode(dto.getClave()));

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
            usuario.setRol(Rol.valueOf(dto.getRol().toUpperCase()));

            if (dto.getClave() != null && !dto.getClave().isEmpty()) {
                usuario.setClave(passwordEncoder.encode(dto.getClave()));
            }

            // Actualización de relaciones por nombre
            if (dto.getLocalidad() != null) {
                usuario.setLocalidad(localidadRepository.findByNombre(dto.getLocalidad())
                        .orElse(usuario.getLocalidad()));
            }
            if (dto.getProvincia() != null) {
                usuario.setProvincia(provinciaRepository.findByNombre(dto.getProvincia())
                        .orElse(usuario.getProvincia()));
            }
            if (dto.getPais() != null) {
                usuario.setPais(paisRepository.findByNombre(dto.getPais())
                        .orElse(usuario.getPais()));
            }

            Usuario actualizado = usuarioRepository.save(usuario);
            return convertirADTO(actualizado);
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

    // Mapper privado para mantener la seguridad y el desacoplamiento
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

        // Devolvemos nombres en lugar de IDs para que el front-end los muestre fácil
        if (usuario.getLocalidad() != null) dto.setLocalidad(usuario.getLocalidad().getNombre());
        if (usuario.getProvincia() != null) dto.setProvincia(usuario.getProvincia().getNombre());
        if (usuario.getPais() != null) dto.setPais(usuario.getPais().getNombre());

        return dto;
    }
}