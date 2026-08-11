package com.example.temaJar.servicios;

import com.example.temaJar.dtos.HabilidadesDTO;
import com.example.temaJar.enumeracion.Habilidad;
import com.example.temaJar.models.Habilidades;
import com.example.temaJar.repository.HabilidadesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HabilidadesServicio {

    @Autowired
    private HabilidadesRepository habilidadesRepository;

    @Transactional(readOnly = true)
    public Habilidades obtenerPorNombre(String nombre) {
        try {
            // Convertimos el String a Enum antes de buscar en la base de datos
            Habilidad habilidadEnum = Habilidad.valueOf(nombre.toUpperCase().replace(" ", "_"));
            return habilidadesRepository.findByNombre(habilidadEnum).orElse(null);
        } catch (IllegalArgumentException e) {
            return null; // Si el nombre enviado no existe en el Enum
        }
    }

    @Transactional(readOnly = true)
    public List<Habilidades> obtenerTodo() {
        return habilidadesRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Habilidades obtenerPorId(Long id) {
        return habilidadesRepository.findById(id).orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Habilidades crear(HabilidadesDTO dto) {
        Habilidades habilidades = new Habilidades();

        // Convertimos el String del DTO al Enum de la entidad
        // .replace(" ", "_") es útil por si envían "Java Spring" y en el Enum es JAVA_SPRING
        habilidades.setNombre(Habilidad.valueOf(dto.getNombre().toUpperCase().trim().replace(" ", "_")));

        return habilidadesRepository.save(habilidades);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public boolean eliminar(Long id) {
        if (habilidadesRepository.existsById(id)) {
            habilidadesRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Habilidades modificar(Long id, HabilidadesDTO dto) {
        return habilidadesRepository.findById(id).map(habilidadExistente -> {
            try {
                // Actualizamos el nombre convirtiendo el String a Enum
                Habilidad nuevoNombre = Habilidad.valueOf(dto.getNombre().toUpperCase().trim().replace(" ", "_"));
                habilidadExistente.setNombre(nuevoNombre);
                return habilidadesRepository.save(habilidadExistente);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("La habilidad '" + dto.getNombre() + "' no es una opción válida del sistema.");
            }
        }).orElse(null);
    }
}
