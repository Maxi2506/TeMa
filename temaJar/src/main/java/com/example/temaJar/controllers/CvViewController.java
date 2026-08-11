package com.example.temaJar.controllers;

import com.example.temaJar.dtos.Cv_AdaptadoDTO;
import com.example.temaJar.dtos.Cv_ArchivoDTO;
import com.example.temaJar.enumeracion.Categoria;
import com.example.temaJar.servicios.Cv_AdaptadoServicio;
import com.example.temaJar.servicios.Cv_ArchivoServicio;
import com.example.temaJar.servicios.CvParserServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class CvViewController {

    @Autowired private Cv_ArchivoServicio cvArchivoServicio;
    @Autowired private Cv_AdaptadoServicio cvAdaptadoServicio;
    @Autowired private CvParserServicio cvParserServicio; // <-- NUEVO

    @PostMapping("/cargacv")
    public String procesarYMostrarCv(@RequestParam("file") MultipartFile file,
                                     @RequestParam(value = "idUsuario", required = false, defaultValue = "1") Long idUsuario,
                                     Model model) {
        try {
            Cv_ArchivoDTO cvProcesado = cvArchivoServicio.procesarYGuardarArchivo(file, idUsuario);
            model.addAttribute("cv", cvProcesado);
            return "detalle-cv";
        } catch (Exception e) {
            model.addAttribute("error", "Ocurrió un error al procesar el archivo: " + e.getMessage());
            return "carga-cv";
        }
    }

    @GetMapping("/vistas/seleccion-categoria")
    public String mostrarSeleccionCategoria(@RequestParam("id") Long idCvArchivo, Model model) {
        try {
            Cv_ArchivoDTO cv = cvArchivoServicio.obtenerPorId(idCvArchivo);
            model.addAttribute("cv", cv);
            model.addAttribute("categorias", Categoria.values());
            return "seleccion-categoria";
        } catch (Exception e) {
            model.addAttribute("error", "No se encontró el CV: " + e.getMessage());
            return "carga-cv";
        }
    }

    @PostMapping("/adaptar-cv")
    public String adaptarCv(@RequestParam("idCvArchivo") Long idCvArchivo,
                            @RequestParam("categoria") Categoria categoria,
                            Model model) {
        try {
            Cv_ArchivoDTO cvArchivo = cvArchivoServicio.obtenerPorId(idCvArchivo);
            Cv_AdaptadoDTO adaptado = cvAdaptadoServicio.generarAdaptacion(cvArchivo, categoria);

            // NUEVO: Parsear el JSON de Gemini en campos estructurados
            CvParserServicio.CvParseado cvParseado = cvParserServicio.parsearCvJson(adaptado.getContenido());

            model.addAttribute("cv", cvArchivo);
            model.addAttribute("adaptado", adaptado);
            model.addAttribute("categoria", categoria);
            model.addAttribute("cvParseado", cvParseado); // <-- Para el HTML estilizado

            return "detalle-cv-adaptado";

        } catch (Exception e) {
            try {
                Cv_ArchivoDTO cv = cvArchivoServicio.obtenerPorId(idCvArchivo);
                model.addAttribute("cv", cv);
            } catch (Exception ex) {}

            model.addAttribute("error", "Error al adaptar el CV: " + e.getMessage());
            model.addAttribute("categorias", Categoria.values());
            return "seleccion-categoria";
        }
    }
}