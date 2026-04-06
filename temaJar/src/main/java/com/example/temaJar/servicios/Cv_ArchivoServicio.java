package com.example.temaJar.servicios;

import com.example.temaJar.dtos.Cv_ArchivoDTO;
import com.example.temaJar.models.Cv_Archivo;
import com.example.temaJar.models.Usuario;
import com.example.temaJar.repository.Cv_ArchivoRepository;
import com.example.temaJar.repository.UsuarioRepository; // Necesario para la relación
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Cv_ArchivoServicio {

    @Autowired
    private Cv_ArchivoRepository cvArchivoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<Cv_ArchivoDTO> obtenerTodo() {
        return cvArchivoRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Cv_ArchivoDTO obtenerPorId(Long id) {
        return cvArchivoRepository.findById(id)
                .map(this::convertirADTO)
                .orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Cv_ArchivoDTO crear(Cv_ArchivoDTO dto) {
        Cv_Archivo archivo = new Cv_Archivo();

        // Validación de Integridad: Buscamos al usuario dueño del archivo
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario()).orElse(null);
        archivo.setNombre_usuario(usuario);

        archivo.setNombre_archivo(dto.getNombreArchivo());
        archivo.setFecha_carga(dto.getFechaCarga());
        archivo.setTexto_extraido(dto.getTextoExtraido());

        Cv_Archivo guardado = cvArchivoRepository.save(archivo);
        return convertirADTO(guardado);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public boolean eliminar(Long id) {
        if (cvArchivoRepository.existsById(id)) {
            cvArchivoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Cv_ArchivoDTO modificar(Long id, Cv_ArchivoDTO dto) throws Exception {
        return cvArchivoRepository.findById(id).map(archivo -> {
            // Actualizamos la relación con el usuario si es necesario
            Usuario usuario = usuarioRepository.findById(dto.getIdUsuario()).orElse(null);
            archivo.setNombre_usuario(usuario);

            archivo.setNombre_archivo(dto.getNombreArchivo());
            archivo.setFecha_carga(dto.getFechaCarga());
            archivo.setTexto_extraido(dto.getTextoExtraido());

            Cv_Archivo actualizado = cvArchivoRepository.save(archivo);
            return convertirADTO(actualizado);
        }).orElse(null);
    }

    // Mapper: Control de Calidad para el flujo de datos
    private Cv_ArchivoDTO convertirADTO(Cv_Archivo entidad) {
        return new Cv_ArchivoDTO(
                entidad.getId(),
                entidad.getNombre_usuario() != null ? entidad.getNombre_usuario().getId() : null,
                entidad.getNombre_archivo(),
                entidad.getFecha_carga(),
                entidad.getTexto_extraido()
        );
    }
}
