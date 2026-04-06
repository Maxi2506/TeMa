package com.example.temaJar.controllers;

import com.example.temaJar.dtos.Cv_AdaptadoDTO;
import com.example.temaJar.servicios.Cv_AdaptadoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cvadaptado")
public class Cv_AdaptadoController {

    @Autowired
    private Cv_AdaptadoServicio cvAdaptadoServicio;

    @GetMapping
    public List<Cv_AdaptadoDTO> getAll() {
        return cvAdaptadoServicio.obtenerTodo();
    }

    @GetMapping("/{id}")
    public Cv_AdaptadoDTO getById(@PathVariable Long id) {
        return cvAdaptadoServicio.obtenerPorId(id);
    }

    @PostMapping("/registro")
    public Cv_AdaptadoDTO create(@RequestBody Cv_AdaptadoDTO cvAdaptado) {
        return cvAdaptadoServicio.crear(cvAdaptado);
    }

    @PutMapping("/{id}")
    public Cv_AdaptadoDTO update(@PathVariable Long id, @RequestBody Cv_AdaptadoDTO cvAdaptado) throws Exception {
        return cvAdaptadoServicio.modificar(id, cvAdaptado);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        if (cvAdaptadoServicio.eliminar(id)) {
            return "CV Adaptado con ID " + id + " eliminado correctamente";
        } else {
            return "CV Adaptado con ID " + id + " no encontrado";
        }
    }
}
