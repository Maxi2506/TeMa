package com.example.temaJar.controllers;

import com.example.temaJar.dtos.PuestoDTO;
import com.example.temaJar.models.Puesto;
import com.example.temaJar.servicios.PuestoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/puesto")
public class PuestoController {

    @Autowired
    private PuestoServicio puestoServicio;

    @GetMapping
    public List<Puesto> getAll() {
        return puestoServicio.obtenerTodo();
    }

    @GetMapping("/{id}")
    public Puesto getById(@PathVariable Long id) {
        return puestoServicio.obtenerPorId(id);
    }

    @GetMapping("/categoria/{categoria}")
    public List<Puesto> getByCategoria(@PathVariable String categoria) {
        return puestoServicio.obtenerPorCategoria(categoria);
    }

    @PostMapping("/registro")
    public Puesto create(@RequestBody PuestoDTO puestoDTO) {
        // Recibimos el DTO y delegamos al servicio la creación
        return puestoServicio.crear(puestoDTO);
    }

    @PutMapping("/{id}")
    public Puesto update(@PathVariable Long id, @RequestBody PuestoDTO puestoDTO) {
        return puestoServicio.modificar(id, puestoDTO);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        if (puestoServicio.eliminar(id)) {
            return "Puesto con ID " + id + " eliminado correctamente.";
        } else {
            return "Puesto con ID " + id + " no encontrado.";
        }
    }

    @GetMapping("/count")
    public long count() {
        return puestoServicio.contar();
    }
}
