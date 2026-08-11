package com.example.temaJar.controllers;

import com.example.temaJar.dtos.ProvinciaDTO;
import com.example.temaJar.servicios.ProvinciaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/provincia")
public class ProvinciaController {

    @Autowired
    private ProvinciaServicio provinciaServicio;

    @GetMapping
    public List<ProvinciaDTO> getAll() {
        return provinciaServicio.obtenerTodo();
    }

    @GetMapping("/{id}")
    public ProvinciaDTO getById(@PathVariable Long id) {
        return provinciaServicio.obtenerPorId(id);
    }

    @PostMapping("/registro")
    public ProvinciaDTO create(@RequestBody ProvinciaDTO provinciaDTO) {
        return provinciaServicio.crear(provinciaDTO);
    }

    @PutMapping("/{id}")
    public ProvinciaDTO update(@PathVariable Long id, @RequestBody ProvinciaDTO provinciaDTO) throws Exception {
        return provinciaServicio.modificar(id, provinciaDTO);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        if (provinciaServicio.eliminar(id)) {
            return "Provincia con ID " + id + " eliminada correctamente.";
        } else {
            return "Provincia con ID " + id + " no encontrada.";
        }
    }
}
