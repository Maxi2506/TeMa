package com.example.temaJar.servicios;

import com.example.temaJar.dtos.LocalidadDTO;
import com.example.temaJar.models.Localidad;
import com.example.temaJar.models.Provincia;
import com.example.temaJar.repository.LocalidadRepository;
import com.example.temaJar.repository.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocalidadServicio {

    @Autowired
    private LocalidadRepository localidadRepository;

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Transactional(readOnly = true)
    public List<LocalidadDTO> obtenerTodo() {
        return localidadRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public LocalidadDTO obtenerPorId(Long id) {
        return localidadRepository.findById(id)
                .map(this::convertirADTO)
                .orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public LocalidadDTO crear(LocalidadDTO dto) {
        Localidad localidad = new Localidad();
        localidad.setNombre(dto.getNombre()); // "Mendoza" (Localidad)

        // Búsqueda por NOMBRE de la provincia
        Provincia provincia = provinciaRepository.findByNombre(dto.getNombreProvincia())
                .orElseThrow(() -> new RuntimeException("Error: La provincia '" + dto.getNombreProvincia() + "' no existe."));

        localidad.setProvincia(provincia);

        Localidad guardada = localidadRepository.save(localidad);
        return convertirADTO(guardada);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public LocalidadDTO modificar(Long id, LocalidadDTO dto) throws Exception {
        return localidadRepository.findById(id).map(localidad -> {
            localidad.setNombre(dto.getNombre());

            // Actualizamos buscando por nombre
            if (dto.getNombreProvincia() != null) {
                Provincia provincia = provinciaRepository.findByNombre(dto.getNombreProvincia())
                        .orElse(localidad.getProvincia());
                localidad.setProvincia(provincia);
            }

            Localidad actualizada = localidadRepository.save(localidad);
            return convertirADTO(actualizada);
        }).orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public boolean eliminar(Long id) {
        if (localidadRepository.existsById(id)) {
            localidadRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private LocalidadDTO convertirADTO(Localidad localidad) {
        LocalidadDTO dto = new LocalidadDTO();
        dto.setId(localidad.getId());
        dto.setNombre(localidad.getNombre());
        if (localidad.getProvincia() != null) {
            dto.setNombreProvincia(localidad.getProvincia().getNombre());
            // Si el DTO de Localidad tiene Pais, podrías sumarlo acá también
        }
        return dto;
    }
}
