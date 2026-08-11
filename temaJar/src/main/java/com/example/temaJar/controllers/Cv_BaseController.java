package com.example.temaJar.controllers;

import com.example.temaJar.dtos.Cv_BaseDTO;
import com.example.temaJar.models.Cv_Base;
import com.example.temaJar.repository.Cv_BaseRepository;
import com.example.temaJar.servicios.Cv_BaseServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cvbase")
public class Cv_BaseController {

    @Autowired
    private Cv_BaseServicio cvBaseServicio;

    private Cv_BaseRepository cvBaseRepositorio;

    // --- LISTAR TODOS ---
    @GetMapping
    public List<Cv_BaseDTO> obtenerTodo() {
        // El controlador le pide la lista ya convertida al servicio
        return cvBaseServicio.obtenerTodo();
    }

    // --- BUSCAR POR ID ---
    @GetMapping("/{id}")
    public Cv_BaseDTO getById(@PathVariable Long id) {
        return cvBaseServicio.obtenerPorId(id);
    }

    // --- CREAR NUEVO CV ---
    @PostMapping("/registro")
    public Cv_BaseDTO create(@RequestBody Cv_BaseDTO cvBaseDTO) {
        // Al usar el DTO con nombres, el JSON de Postman se vinculará aquí automáticamente
        return cvBaseServicio.crear(cvBaseDTO);
    }

    // --- MODIFICAR CV EXISTENTE ---
    @PutMapping("/{id}")
    public Cv_BaseDTO update(@PathVariable Long id, @RequestBody Cv_BaseDTO cvBaseDTO) {
        // Quitamos el 'throws Exception' porque el Servicio ya maneja los errores internamente
        return cvBaseServicio.modificar(id, cvBaseDTO);
    }

    // --- ELIMINAR CV ---
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        if (cvBaseServicio.eliminar(id)) {
            return "CV Base con ID " + id + " eliminado correctamente";
        } else {
            return "CV Base con ID " + id + " no encontrado";
        }
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        e.printStackTrace(); // Esto asegura que lo veamos en la consola de IntelliJ
        return "Error detectado: " + e.getMessage();
    }

}