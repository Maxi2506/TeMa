package com.example.temaJar.controllers;

import com.example.temaJar.dtos.Cv_HabilidadesDTO;
import com.example.temaJar.servicios.Cv_HabilidadesServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cvhabilidades")
public class Cv_HabilidadesController {

    @Autowired
    private Cv_HabilidadesServicio cvHabilidadesServicio;

    @GetMapping
    public List<Cv_HabilidadesDTO> getAll() {
        return cvHabilidadesServicio.obtenerTodo();
    }

    @GetMapping("/{id}")
    public Cv_HabilidadesDTO getById(@PathVariable Long id) {
        return cvHabilidadesServicio.obtenerPorId(id);
    }

    @PostMapping("/registro")
    public Cv_HabilidadesDTO create(@RequestBody Cv_HabilidadesDTO dto) {
        return cvHabilidadesServicio.crear(dto);
    }

    @PutMapping("/{id}")
    public Cv_HabilidadesDTO update(@PathVariable Long id, @RequestBody Cv_HabilidadesDTO dto) throws Exception {
        return cvHabilidadesServicio.modificar(id, dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        if (cvHabilidadesServicio.eliminar(id)) {
            return "Habilidad con ID " + id + " eliminada correctamente";
        } else {
            return "Habilidad con ID " + id + " no encontrada";
        }
    }
}
