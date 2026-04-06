package com.example.temaJar.controllers;

import com.example.temaJar.dtos.LocalidadDTO;
import com.example.temaJar.servicios.LocalidadServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/localidad")
public class LocalidadController {

    @Autowired
    private LocalidadServicio localidadServicio;

    @GetMapping
    public List<LocalidadDTO> getAll() {
        return localidadServicio.obtenerTodo();
    }

    @GetMapping("/{id}")
    public LocalidadDTO getById(@PathVariable Long id) {
        return localidadServicio.obtenerPorId(id);
    }

    @PostMapping("/registro")
    public LocalidadDTO create(@RequestBody LocalidadDTO localidadDTO) {
        return localidadServicio.crear(localidadDTO);
    }

    @PutMapping("/{id}")
    public LocalidadDTO update(@PathVariable Long id, @RequestBody LocalidadDTO localidadDTO) throws Exception {
        return localidadServicio.modificar(id, localidadDTO);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        if (localidadServicio.eliminar(id)) {
            return "Localidad con ID " + id + " eliminada correctamente.";
        } else {
            return "Localidad con ID " + id + " no encontrada.";
        }
    }
}