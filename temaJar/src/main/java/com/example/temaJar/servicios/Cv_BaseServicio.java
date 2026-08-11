package com.example.temaJar.servicios;

import com.example.temaJar.dtos.Cv_BaseDTO;
import com.example.temaJar.enumeracion.Habilidad;
import com.example.temaJar.models.*;
import com.example.temaJar.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Cv_BaseServicio {

    @Autowired private Cv_BaseRepository cvBaseRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private LocalidadRepository localidadRepository;
    @Autowired private ProvinciaRepository provinciaRepository;
    @Autowired private PaisRepository paisRepository;

    @Transactional(readOnly = true)
    public List<Cv_BaseDTO> obtenerTodo() {
        return cvBaseRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Cv_BaseDTO obtenerPorId(Long id) {
        return cvBaseRepository.findById(id)
                .map(this::convertirADTO)
                .orElseThrow(() -> new RuntimeException("No se encontró el CV con ID: " + id));
    }

    @Transactional
    public Cv_BaseDTO crear(Cv_BaseDTO dto) {
        try {
            Cv_Base cv = new Cv_Base();
            actualizarDatosBasicos(cv, dto);
            vincularRelaciones(cv, dto);
            return convertirADTO(cvBaseRepository.save(cv));
        } catch (Exception e) {
            // Esto imprimirá el error exacto en la consola de IntelliJ
            System.out.println("ERROR AL CREAR CV: " + e.getMessage());
            throw e;
        }
    }

    @Transactional
    public Cv_BaseDTO modificar(Long id, Cv_BaseDTO dto) {
        return cvBaseRepository.findById(id).map(cv -> {
            actualizarDatosBasicos(cv, dto);
            vincularRelaciones(cv, dto);
            return convertirADTO(cvBaseRepository.save(cv));
        }).orElseThrow(() -> new RuntimeException("No se encontró el CV con ID: " + id));
    }

    @Transactional
    public boolean eliminar(Long id) {
        if (cvBaseRepository.existsById(id)) {
            cvBaseRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // --- MÉTODOS DE APOYO (PRIVATE) ---

    private void actualizarDatosBasicos(Cv_Base cv, Cv_BaseDTO dto) {
        cv.setResumen_profesional(dto.getResumenProfesional());
        cv.setTelefono(dto.getTelefono());
        cv.setDomicilio(dto.getDomicilio());
        cv.setLinkedin_url(dto.getLinkedinUrl());
    }

    private void vincularRelaciones(Cv_Base entidad, Cv_BaseDTO dto) {
        // 1. Vincular Usuario (Usamos findFirst para evitar el error de "2 results")
        if (dto.getCorreoUsuario() != null && !dto.getCorreoUsuario().isEmpty()) {
            Usuario user = usuarioRepository.findFirstByCorreo(dto.getCorreoUsuario())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + dto.getCorreoUsuario()));
            entidad.setUsuario(user);
        }

        // 2. Vincular Geografía (Búsqueda jerárquica con findFirst)
        if (dto.getNombrePais() != null) {
            Pais p = paisRepository.findFirstByNombre(dto.getNombrePais())
                    .orElseThrow(() -> new RuntimeException("País no encontrado: " + dto.getNombrePais()));
            entidad.setPais(p);

            if (dto.getNombreProvincia() != null) {
                Provincia prov = provinciaRepository.findFirstByNombreAndPais(dto.getNombreProvincia(), p)
                        .orElseThrow(() -> new RuntimeException("Provincia no encontrada en " + p.getNombre()));
                entidad.setProvincia(prov);

                if (dto.getNombreLocalidad() != null) {
                    Localidad loc = localidadRepository.findFirstByNombreAndProvincia(dto.getNombreLocalidad(), prov)
                            .orElseThrow(() -> new RuntimeException("Localidad no encontrada en " + prov.getNombre()));
                    entidad.setLocalidad(loc);
                }
            }
        }

        // 3. Vincular Habilidades (Lógica anti-error de tipeo)
        if (dto.getHabilidadesNombres() != null && !dto.getHabilidadesNombres().isEmpty()) {
            List<Habilidad> listaEnums = dto.getHabilidadesNombres().stream()
                    .map(hNombre -> {
                        // Normalizamos el texto del JSON
                        String nombreProcesado = hNombre.toUpperCase().trim().replace(" ", "_");

                        try {
                            // Intento 1: Con el nombre procesado (ej: SPRING_BOOT)
                            return Habilidad.valueOf(nombreProcesado);
                        } catch (IllegalArgumentException e1) {
                            try {
                                // Intento 2: Sin guiones (ej: SPRINGBOOT) por si tu Enum no los tiene
                                return Habilidad.valueOf(nombreProcesado.replace("_", ""));
                            } catch (IllegalArgumentException e2) {
                                throw new RuntimeException("La habilidad '" + hNombre + "' no coincide con ninguna opción del sistema.");
                            }
                        }
                    })
                    .collect(Collectors.toList());

            entidad.setHabilidades(listaEnums);
        }
    }

    public Cv_BaseDTO convertirADTO(Cv_Base entidad) {
        if (entidad == null) return null;

        Cv_BaseDTO dto = new Cv_BaseDTO();
        dto.setId(entidad.getId());

        // Evitamos NullPointerException si la entidad no tiene usuario
        if (entidad.getUsuario() != null) {
            dto.setCorreoUsuario(entidad.getUsuario().getCorreo());
        }

        dto.setResumenProfesional(entidad.getResumen_profesional());
        dto.setTelefono(entidad.getTelefono());
        dto.setDomicilio(entidad.getDomicilio());
        dto.setLinkedinUrl(entidad.getLinkedin_url());

        // Geografía con protección null
        if (entidad.getLocalidad() != null) dto.setNombreLocalidad(entidad.getLocalidad().getNombre());
        if (entidad.getProvincia() != null) dto.setNombreProvincia(entidad.getProvincia().getNombre());
        if (entidad.getPais() != null) dto.setNombrePais(entidad.getPais().getNombre());

        if (entidad.getHabilidades() != null) {
            dto.setHabilidadesNombres(entidad.getHabilidades().stream()
                    .map(Enum::name)
                    .collect(Collectors.toList()));
        }
        return dto;
    }
}