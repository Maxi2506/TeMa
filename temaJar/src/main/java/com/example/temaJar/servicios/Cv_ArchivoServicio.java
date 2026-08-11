package com.example.temaJar.servicios;

import com.example.temaJar.dtos.Cv_ArchivoDTO;
import com.example.temaJar.models.Cv_Archivo;
import com.example.temaJar.models.Usuario;
import com.example.temaJar.repository.Cv_ArchivoRepository;
import com.example.temaJar.repository.UsuarioRepository;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Cv_ArchivoServicio {

    @Autowired private Cv_ArchivoRepository cvArchivoRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private ChatServicio chatServicio;

    private final Tika tika = new Tika();

    @Transactional
    public Cv_ArchivoDTO procesarYGuardarArchivo(MultipartFile file, Long idUsuario) {
        try {
            // 1. Validar que el archivo no esté vacío
            if (file.isEmpty()) {
                throw new RuntimeException("El archivo está vacío.");
            }

            // 2. Validar tipo de archivo (PDF o Word)
            String mimeType = tika.detect(file.getInputStream());
            System.out.println("--- MIME TYPE DETECTADO: " + mimeType + " ---");

            if (!mimeType.equals("application/pdf")
                    && !mimeType.contains("word")
                    && !mimeType.contains("officedocument")) {
                throw new RuntimeException("Formato no permitido. Solo PDF o Word. Detectado: " + mimeType);
            }

            // 3. Extraer texto bruto con Tika
            String textoBruto = tika.parseToString(file.getInputStream());
            if (textoBruto == null || textoBruto.trim().isEmpty()) {
                throw new RuntimeException("No se pudo extraer texto del archivo.");
            }

            System.out.println("--- TEXTO EXTRAÍDO POR TIKA (primeros 500 chars) ---");
            System.out.println(textoBruto.substring(0, Math.min(textoBruto.length(), 500)));
            System.out.println("------------------------------------------------------");

            // 4. Buscar Usuario
            Usuario usuario = usuarioRepository.findById(idUsuario)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + idUsuario));

            // 5. ANALIZAR CON IA (Llamada al ChatServicio)
            // Si esto falla, @Transactional hará ROLLBACK y NO se guarda nada en la BD
            String analisisIA = chatServicio.procesarTextoConIA(textoBruto);

            System.out.println("--- ANALISIS IA RECIBIDO (primeros 500 chars) ---");
            System.out.println(analisisIA.substring(0, Math.min(analisisIA.length(), 500)));
            System.out.println("---------------------------------------------------");

            // 6. Crear y persistir la entidad
            Cv_Archivo archivo = new Cv_Archivo();
            archivo.setUsuario(usuario);
            archivo.setNombreArchivo(file.getOriginalFilename());
            archivo.setFechaCarga(LocalDate.now());
            archivo.setTextoExtraido(analisisIA);

            Cv_Archivo guardado = cvArchivoRepository.save(archivo);

            System.out.println("--- GUARDADO EN BD CON ID: " + guardado.getId() + " ---");

            return convertirADTO(guardado);

        } catch (RuntimeException e) {
            // Re-lanzamos para que el Controller lo capture y @Transactional haga rollback
            System.err.println("ERROR en procesarYGuardarArchivo: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("ERROR inesperado: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al procesar el CV: " + e.getMessage());
        }
    }

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
                .orElseThrow(() -> new RuntimeException("Archivo no encontrado con ID: " + id));
    }

    @Transactional
    public boolean eliminar(Long id) {
        if (cvArchivoRepository.existsById(id)) {
            cvArchivoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private Cv_ArchivoDTO convertirADTO(Cv_Archivo entidad) {
        Cv_ArchivoDTO dto = new Cv_ArchivoDTO();
        dto.setId(entidad.getId());
        dto.setIdUsuario(entidad.getUsuario() != null ? entidad.getUsuario().getId() : null);
        dto.setNombreArchivo(entidad.getNombreArchivo());
        dto.setFechaCarga(entidad.getFechaCarga());
        dto.setTextoExtraido(entidad.getTextoExtraido());
        return dto;
    }
}
