package com.example.temaJar.servicios;

import com.example.temaJar.dtos.Cv_AdaptadoDTO;
import com.example.temaJar.models.Cv_Adaptado;
import com.example.temaJar.models.Cv_Base;
import com.example.temaJar.models.Puesto;
import com.example.temaJar.repository.Cv_AdaptadoRepository;
import com.example.temaJar.repository.Cv_BaseRepository; // Necesario para validar
import com.example.temaJar.repository.PuestoRepository;   // Necesario para validar
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Cv_AdaptadoServicio {

    @Autowired
    private Cv_AdaptadoRepository cvAdaptadoRepository;

    @Autowired
    private Cv_BaseRepository cvBaseRepository;

    @Autowired
    private PuestoRepository puestoRepository;

    @Transactional(readOnly = true)
    public List<Cv_AdaptadoDTO> obtenerTodo() {
        return cvAdaptadoRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Cv_AdaptadoDTO obtenerPorId(Long id) {
        return cvAdaptadoRepository.findById(id)
                .map(this::convertirADTO)
                .orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Cv_AdaptadoDTO crear(Cv_AdaptadoDTO dto) {
        Cv_Adaptado cvAdaptado = new Cv_Adaptado();

        // Buscamos y validamos las entidades relacionadas (Control de Integridad)
        Cv_Base cvBase = cvBaseRepository.findById(dto.getIdCvBase()).orElse(null);
        Puesto puesto = puestoRepository.findById(dto.getIdPuesto()).orElse(null);

        cvAdaptado.setCv_base(cvBase);
        cvAdaptado.setPuesto(puesto);
        cvAdaptado.setContenido(dto.getContenido());
        cvAdaptado.setFecha_create(dto.getFechaCreate());

        Cv_Adaptado guardado = cvAdaptadoRepository.save(cvAdaptado);
        return convertirADTO(guardado);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public boolean eliminar(Long id) {
        if (cvAdaptadoRepository.existsById(id)) {
            cvAdaptadoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Cv_AdaptadoDTO modificar(Long id, Cv_AdaptadoDTO dto) throws Exception {
        return cvAdaptadoRepository.findById(id).map(cv -> {
            // Actualizamos relaciones
            Cv_Base cvBase = cvBaseRepository.findById(dto.getIdCvBase()).orElse(null);
            Puesto puesto = puestoRepository.findById(dto.getIdPuesto()).orElse(null);

            cv.setCv_base(cvBase);
            cv.setPuesto(puesto);
            cv.setContenido(dto.getContenido());
            cv.setFecha_create(dto.getFechaCreate());

            Cv_Adaptado actualizado = cvAdaptadoRepository.save(cv);
            return convertirADTO(actualizado);
        }).orElse(null);
    }

    // Mapper: Convierte Entidad a DTO para cumplir con el aislamiento de capas
    private Cv_AdaptadoDTO convertirADTO(Cv_Adaptado entidad) {
        return new Cv_AdaptadoDTO(
                entidad.getId(),
                entidad.getCv_base() != null ? entidad.getCv_base().getId() : null,
                entidad.getPuesto() != null ? entidad.getPuesto().getId() : null,
                entidad.getContenido(),
                entidad.getFecha_create()
        );
    }
}
