package com.example.temaJar.servicios;

import com.example.temaJar.dtos.Cv_HabilidadesDTO;
import com.example.temaJar.models.Cv_Habilidades;
import com.example.temaJar.models.Cv_Base;
import com.example.temaJar.models.Habilidades;
import com.example.temaJar.repository.Cv_HabilidadesRepository;
import com.example.temaJar.repository.Cv_BaseRepository;
import com.example.temaJar.repository.HabilidadesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Cv_HabilidadesServicio {

    @Autowired
    private Cv_HabilidadesRepository cvHabilidadesRepository;

    @Autowired
    private Cv_BaseRepository cvBaseRepository;

    @Autowired
    private HabilidadesRepository habilidadesRepository;

    @Transactional(readOnly = true)
    public List<Cv_HabilidadesDTO> obtenerTodo() {
        return cvHabilidadesRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Cv_HabilidadesDTO obtenerPorId(Long id) {
        return cvHabilidadesRepository.findById(id)
                .map(this::convertirADTO)
                .orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Cv_HabilidadesDTO crear(Cv_HabilidadesDTO dto) {
        Cv_Habilidades cvHabilidad = new Cv_Habilidades();

        // Buscamos las entidades relacionadas para asegurar la integridad de datos
        Cv_Base cvBase = cvBaseRepository.findById(dto.getIdCvBase()).orElse(null);
        Habilidades habilidadCatalogo = habilidadesRepository.findById(dto.getIdHabilidad()).orElse(null);

        cvHabilidad.setCv_base(cvBase);
        cvHabilidad.setHabilidades(habilidadCatalogo);

        Cv_Habilidades guardado = cvHabilidadesRepository.save(cvHabilidad);
        return convertirADTO(guardado);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Cv_HabilidadesDTO modificar(Long id, Cv_HabilidadesDTO dto) throws Exception {
        return cvHabilidadesRepository.findById(id).map(cvHabilidad -> {

            // Actualizamos las relaciones buscando en los repositorios correspondientes
            Cv_Base cvBase = cvBaseRepository.findById(dto.getIdCvBase()).orElse(null);
            Habilidades habilidadCatalogo = habilidadesRepository.findById(dto.getIdHabilidad()).orElse(null);

            cvHabilidad.setCv_base(cvBase);
            cvHabilidad.setHabilidades(habilidadCatalogo);

            Cv_Habilidades actualizado = cvHabilidadesRepository.save(cvHabilidad);
            return convertirADTO(actualizado);
        }).orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public boolean eliminar(Long id) {
        if (cvHabilidadesRepository.existsById(id)) {
            cvHabilidadesRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private Cv_HabilidadesDTO convertirADTO(Cv_Habilidades entidad) {
        Cv_HabilidadesDTO dto = new Cv_HabilidadesDTO();
        dto.setId(entidad.getId());

        // Mapeo del ID del CV Base
        if (entidad.getCv_base() != null) {
            dto.setIdCvBase(entidad.getCv_base().getId());
        }

        // Mapeo de la información de la Habilidad desde el catálogo
        if (entidad.getHabilidades() != null) {
            dto.setIdHabilidad(entidad.getHabilidades().getId());
            dto.setNombreHabilidad(entidad.getHabilidades().getNombre());
        }

        return dto;
    }
}