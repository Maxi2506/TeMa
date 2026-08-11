package com.example.temaJar.servicios;

import com.example.temaJar.dtos.PaisDTO;
import com.example.temaJar.models.Pais;
import com.example.temaJar.repository.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaisServicio {

    @Autowired
    private PaisRepository paisRepository;

    @Transactional(readOnly = true)
    public List<PaisDTO> obtenerTodo() {
        return paisRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PaisDTO obtenerPorId(Long id) {
        return paisRepository.findById(id)
                .map(this::convertirADTO)
                .orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public PaisDTO crear(PaisDTO dto) {
        if (paisRepository.findByNombre(dto.getNombre()).isPresent()) {
            throw new RuntimeException("El país '" + dto.getNombre() + "' ya existe en el sistema.");
        }

        Pais pais = new Pais();
        pais.setNombre(dto.getNombre());

        Pais guardado = paisRepository.save(pais);
        return convertirADTO(guardado);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public PaisDTO modificar(Long id, PaisDTO dto) {
        return paisRepository.findById(id).map(pais -> {
            pais.setNombre(dto.getNombre());
            Pais actualizado = paisRepository.save(pais);
            return convertirADTO(actualizado);
        }).orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public boolean eliminar(Long id) {
        if (paisRepository.existsById(id)) {
            paisRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Mapper consistente con el resto del proyecto
    private PaisDTO convertirADTO(Pais pais) {
        PaisDTO dto = new PaisDTO();
        dto.setId(pais.getId());
        dto.setNombre(pais.getNombre());
        return dto;
    }
}