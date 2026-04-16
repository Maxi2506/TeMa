package com.example.temaJar.servicios;

import com.example.temaJar.dtos.Cv_AdaptadoDTO;
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
public class Cv_AdaptadoServicio {

    @Autowired private Cv_AdaptadoRepository cvAdaptadoRepository;
    @Autowired private Cv_BaseRepository cvBaseRepository;
    @Autowired private PuestoRepository puestoRepository;

    @Transactional(readOnly = true)
    public Cv_AdaptadoDTO obtenerPorId(Long id) {
        return cvAdaptadoRepository.findById(id)
                .map(this::convertirADTO)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Cv_AdaptadoDTO> obtenerTodo() {
        return cvAdaptadoRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public Cv_AdaptadoDTO crear(Cv_AdaptadoDTO dto) {
        Cv_Adaptado cv = new Cv_Adaptado();
        mapearEntidad(cv, dto);
        return convertirADTO(cvAdaptadoRepository.save(cv));
    }

    @Transactional
    public Cv_AdaptadoDTO modificar(Long id, Cv_AdaptadoDTO dto) {
        return cvAdaptadoRepository.findById(id).map(cv -> {
            mapearEntidad(cv, dto);
            return convertirADTO(cvAdaptadoRepository.save(cv));
        }).orElseThrow(() -> new RuntimeException("No se encontró el CV Adaptado con ID: " + id));
    }

    private void mapearEntidad(Cv_Adaptado entidad, Cv_AdaptadoDTO dto) {
        Cv_Base base = cvBaseRepository.findById(dto.getIdCvBase())
                .orElseThrow(() -> new RuntimeException("CV Base no encontrado"));
        Puesto puesto = puestoRepository.findById(dto.getIdPuesto())
                .orElseThrow(() -> new RuntimeException("Puesto no encontrado"));

        entidad.setCvBase(base);
        entidad.setPuesto(puesto);
        entidad.setContenido(dto.getContenido());
        entidad.setFechaCreate(dto.getFechaCreate());

        if (dto.getHabilidadesNombres() != null) {
            List<Habilidad> enums = dto.getHabilidadesNombres().stream()
                    .map(h -> Habilidad.valueOf(h.toUpperCase().trim().replace(" ", "_")))
                    .collect(Collectors.toList());
            entidad.setHabilidades(enums);
        }
    }

    private Cv_AdaptadoDTO convertirADTO(Cv_Adaptado entidad) {
        Cv_AdaptadoDTO dto = new Cv_AdaptadoDTO();
        dto.setId(entidad.getId());
        dto.setIdCvBase(entidad.getCvBase().getId());
        dto.setIdPuesto(entidad.getPuesto().getId());
        dto.setContenido(entidad.getContenido());
        dto.setFechaCreate(entidad.getFechaCreate());

        dto.setHabilidadesNombres(entidad.getHabilidades().stream()
                .map(Enum::name)
                .collect(Collectors.toList()));
        return dto;
    }

    @Transactional
    public boolean eliminar(Long id) {
        if (cvAdaptadoRepository.existsById(id)) {
            cvAdaptadoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
