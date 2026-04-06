package com.example.temaJar.servicios;

import com.example.temaJar.dtos.Cv_BaseDTO;
import com.example.temaJar.models.*;
import com.example.temaJar.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Cv_BaseServicio {

    @Autowired
    private Cv_BaseRepository cvBaseRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LocalidadRepository localidadRepository;

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Autowired
    private PaisRepository paisRepository;

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
                .orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Cv_BaseDTO crear(Cv_BaseDTO dto) {
        Cv_Base cvBase = new Cv_Base();
        actualizarRelaciones(cvBase, dto);

        cvBase.setResumen_profesional(dto.getResumenProfesional());
        cvBase.setTelefono(dto.getTelefono());
        cvBase.setDomicilio(dto.getDomicilio());
        cvBase.setLinkedin_url(dto.getLinkedinUrl());

        Cv_Base guardado = cvBaseRepository.save(cvBase);
        return convertirADTO(guardado);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public boolean eliminar(Long id) {
        if (cvBaseRepository.existsById(id)) {
            cvBaseRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Cv_BaseDTO modificar(Long id, Cv_BaseDTO dto) throws Exception {
        return cvBaseRepository.findById(id).map(cv -> {
            actualizarRelaciones(cv, dto);

            cv.setResumen_profesional(dto.getResumenProfesional());
            cv.setTelefono(dto.getTelefono());
            cv.setDomicilio(dto.getDomicilio());
            cv.setLinkedin_url(dto.getLinkedinUrl());

            Cv_Base actualizado = cvBaseRepository.save(cv);
            return convertirADTO(actualizado);
        }).orElse(null);
    }

    private void actualizarRelaciones(Cv_Base entidad, Cv_BaseDTO dto) {
        entidad.setNombre_usuario(usuarioRepository.findById(dto.getIdUsuario()).orElse(null));
        entidad.setLocalidad(localidadRepository.findById(dto.getIdLocalidad()).orElse(null));
        entidad.setProvincia(provinciaRepository.findById(dto.getIdProvincia()).orElse(null));
        entidad.setPais(paisRepository.findById(dto.getIdPais()).orElse(null));
    }

    // Mapper: Control de Calidad para el flujo de información
    private Cv_BaseDTO convertirADTO(Cv_Base entidad) {
        return new Cv_BaseDTO(
                entidad.getId(),
                entidad.getNombre_usuario() != null ? entidad.getNombre_usuario().getId() : null,
                entidad.getResumen_profesional(),
                entidad.getTelefono(),
                entidad.getDomicilio(),
                entidad.getLinkedin_url(),
                entidad.getLocalidad() != null ? entidad.getLocalidad().getId() : null,
                entidad.getProvincia() != null ? entidad.getProvincia().getId() : null,
                entidad.getPais() != null ? entidad.getPais().getId() : null
        );
    }
}