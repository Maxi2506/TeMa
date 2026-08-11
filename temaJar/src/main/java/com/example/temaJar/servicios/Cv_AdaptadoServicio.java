package com.example.temaJar.servicios;

import com.example.temaJar.dtos.Cv_AdaptadoDTO;
import com.example.temaJar.dtos.Cv_ArchivoDTO;
import com.example.temaJar.enumeracion.Categoria;
import com.example.temaJar.enumeracion.Habilidad;
import com.example.temaJar.enumeracion.NivelDeExperiencia;
import com.example.temaJar.models.*;
import com.example.temaJar.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Cv_AdaptadoServicio {

    @Autowired private Cv_AdaptadoRepository cvAdaptadoRepository;
    @Autowired private Cv_BaseRepository cvBaseRepository;
    @Autowired private Cv_ArchivoRepository cvArchivoRepository;  // ← NUEVO
    @Autowired private PuestoRepository puestoRepository;
    @Autowired private ChatServicio chatServicio;               // ← NUEVO

    // ================== CRUD EXISTENTE ==================

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
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró el CV Adaptado con ID: " + id));
    }

    @Transactional
    public boolean eliminar(Long id) {
        if (cvAdaptadoRepository.existsById(id)) {
            cvAdaptadoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // ================== NUEVO: ADAPTACIÓN CON IA ==================

    @Transactional
    public Cv_AdaptadoDTO generarAdaptacion(Cv_ArchivoDTO cvArchivoDTO, Categoria categoria) {
        try {
            // 1. Validar texto
            String textoOriginal = cvArchivoDTO.getTextoExtraido();
            if (textoOriginal == null || textoOriginal.trim().isEmpty()) {
                throw new RuntimeException("El CV no tiene texto extraído para adaptar");
            }

            // 2. Obtener la entidad completa Cv_Archivo para acceder al Usuario
            Cv_Archivo cvArchivo = cvArchivoRepository.findById(cvArchivoDTO.getId())
                    .orElseThrow(() -> new RuntimeException("CV Archivo no encontrado con ID: " + cvArchivoDTO.getId()));

            // 3. Llamar a Gemini para adaptar
            String textoAdaptado = chatServicio.adaptarCvParaCategoria(
                    textoOriginal,
                    categoria.name().replace("_", " ")
            );

            System.out.println("--- TEXTO ADAPTADO RECIBIDO (primeros 300 chars) ---");
            System.out.println(textoAdaptado.substring(0, Math.min(textoAdaptado.length(), 300)));
            System.out.println("-----------------------------------------------------");

            // 4. Buscar o crear Cv_Base asociado al USUARIO del CV original
            Cv_Base cvBase = cvBaseRepository.findById(cvArchivoDTO.getId())
                    .orElseGet(() -> {
                        Cv_Base nuevo = new Cv_Base();
                        nuevo.setUsuario(cvArchivo.getUsuario());  // ← FIX: asociar usuario real
                        nuevo.setResumen_profesional("CV generado automáticamente");
                        nuevo.setTelefono(0L);
                        nuevo.setDomicilio("");
                        nuevo.setLinkedin_url("");
                        return cvBaseRepository.save(nuevo);
                    });

            // 5. Buscar o crear Puesto según la categoría
            String nombrePuesto = categoria.name().replace("_", " ");
            Puesto puesto = puestoRepository.findAll().stream()
                    .filter(p -> p.getNombre() != null && p.getNombre().equalsIgnoreCase(nombrePuesto))
                    .findFirst()
                    .orElseGet(() -> {
                        Puesto nuevo = new Puesto();
                        nuevo.setNombre(nombrePuesto);
                        nuevo.setCategoria(categoria);
                        nuevo.setNivel(NivelDeExperiencia.JUNIOR); // Default, puede cambiarse después
                        return puestoRepository.save(nuevo);
                    });

            // 6. Crear Cv_Adaptado
            Cv_Adaptado adaptado = new Cv_Adaptado();
            adaptado.setCvBase(cvBase);
            adaptado.setPuesto(puesto);
            adaptado.setContenido(textoAdaptado);
            adaptado.setFechaCreate(LocalDate.now());
            adaptado.setHabilidades(new ArrayList<>()); // ← FIX: vacío por ahora, evita error de BD

            Cv_Adaptado guardado = cvAdaptadoRepository.save(adaptado);
            return convertirADTO(guardado);

        } catch (Exception e) {
            throw new RuntimeException("Error al generar adaptación: " + e.getMessage());
        }
    }

    // ================== MÉTODOS PRIVADOS ==================

    private void mapearEntidad(Cv_Adaptado entidad, Cv_AdaptadoDTO dto) {
        Cv_Base base = cvBaseRepository.findById(dto.getIdCvBase())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: CV Base con ID " + dto.getIdCvBase() + " no existe."));
        Puesto puesto = puestoRepository.findById(dto.getIdPuesto())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Puesto con ID " + dto.getIdPuesto() + " no existe."));

        entidad.setCvBase(base);
        entidad.setPuesto(puesto);
        entidad.setContenido(dto.getContenido());
        entidad.setFechaCreate(dto.getFechaCreate());

        if (dto.getHabilidadesNombres() != null) {
            List<Habilidad> enums = new ArrayList<>();
            for (String nombre : dto.getHabilidadesNombres()) {
                try {
                    String formato = nombre.toUpperCase().trim().replace(" ", "_");
                    enums.add(Habilidad.valueOf(formato));
                } catch (IllegalArgumentException e) {
                    System.err.println("La habilidad '" + nombre + "' no existe en el catálogo Enum.");
                }
            }
            entidad.setHabilidades(enums);
        }
    }

    private Cv_AdaptadoDTO convertirADTO(Cv_Adaptado entidad) {
        Cv_AdaptadoDTO dto = new Cv_AdaptadoDTO();
        dto.setId(entidad.getId());
        dto.setIdCvBase(entidad.getCvBase() != null ? entidad.getCvBase().getId() : null);
        dto.setIdPuesto(entidad.getPuesto() != null ? entidad.getPuesto().getId() : null);
        dto.setContenido(entidad.getContenido());
        dto.setFechaCreate(entidad.getFechaCreate());

        if (entidad.getHabilidades() != null) {
            dto.setHabilidadesNombres(entidad.getHabilidades().stream()
                    .map(Enum::name)
                    .collect(Collectors.toList()));
        }
        return dto;
    }
}