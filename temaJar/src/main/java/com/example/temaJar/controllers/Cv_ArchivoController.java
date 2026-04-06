package com.example.temaJar.controllers;

import com.example.temaJar.dtos.Cv_ArchivoDTO;
import com.example.temaJar.servicios.Cv_ArchivoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cvarchivo")
public class Cv_ArchivoController {

    @Autowired
    private Cv_ArchivoServicio cvArchivoServicio;

    @GetMapping
    public List<Cv_ArchivoDTO> getAll() {
        return cvArchivoServicio.obtenerTodo();
    }

    @GetMapping("/{id}")
    public Cv_ArchivoDTO getById(@PathVariable Long id) {
        return cvArchivoServicio.obtenerPorId(id);
    }

    @PostMapping("/registro")
    public Cv_ArchivoDTO create(@RequestBody Cv_ArchivoDTO cvArchivo) {
        return cvArchivoServicio.crear(cvArchivo);
    }

    @PutMapping("/{id}")
    public Cv_ArchivoDTO update(@PathVariable Long id, @RequestBody Cv_ArchivoDTO cvArchivo) throws Exception {
        return cvArchivoServicio.modificar(id, cvArchivo);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        if (cvArchivoServicio.eliminar(id)) {
            return "Archivo de CV con ID " + id + " eliminado correctamente";
        } else {
            return "Archivo de CV con ID " + id + " no encontrado";
        }
    }
}
