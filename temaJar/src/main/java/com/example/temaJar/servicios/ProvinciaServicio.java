package com.example.temaJar.servicios;

import com.example.temaJar.dtos.ProvinciaDTO;
import com.example.temaJar.models.Pais;
import com.example.temaJar.models.Provincia;
import com.example.temaJar.repository.PaisRepository;
import com.example.temaJar.repository.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProvinciaServicio {

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Autowired
    private PaisRepository paisRepository;

    @Transactional(readOnly = true)
    public List<ProvinciaDTO> obtenerTodo() {
        return provinciaRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProvinciaDTO obtenerPorId(Long id) {
        return provinciaRepository.findById(id)
                .map(this::convertirADTO)
                .orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public ProvinciaDTO crear(ProvinciaDTO dto) {
        Provincia provincia = new Provincia();
        provincia.setNombre(dto.getNombre()); // "Mendoza" (Provincia)

        // Búsqueda por NOMBRE del país
        Pais pais = paisRepository.findByNombre(dto.getNombrePais())
                .orElseThrow(() -> new RuntimeException("Error: El país '" + dto.getNombrePais() + "' no existe."));

        provincia.setPais(pais);

        Provincia guardada = provinciaRepository.save(provincia);
        return convertirADTO(guardada);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public ProvinciaDTO modificar(Long id, ProvinciaDTO dto) throws Exception {
        return provinciaRepository.findById(id).map(provincia -> {
            provincia.setNombre(dto.getNombre());

            if (dto.getNombrePais() != null) {
                Pais pais = paisRepository.findByNombre(dto.getNombrePais())
                        .orElse(provincia.getPais());
                provincia.setPais(pais);
            }

            Provincia actualizada = provinciaRepository.save(provincia);
            return convertirADTO(actualizada);
        }).orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public boolean eliminar(Long id) {
        if (provinciaRepository.existsById(id)) {
            provinciaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private ProvinciaDTO convertirADTO(Provincia provincia) {
        ProvinciaDTO dto = new ProvinciaDTO();
        dto.setId(provincia.getId());
        dto.setNombre(provincia.getNombre());
        if (provincia.getPais() != null) {
            dto.setNombrePais(provincia.getPais().getNombre());
        }
        return dto;
    }
}