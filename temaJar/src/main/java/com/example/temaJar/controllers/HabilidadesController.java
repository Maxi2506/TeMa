package com.example.temaJar.controllers;

import com.example.temaJar.dtos.HabilidadesDTO;
import com.example.temaJar.models.Habilidades;
import com.example.temaJar.servicios.HabilidadesServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/habilidades")
public class HabilidadesController {

    @Autowired
    private HabilidadesServicio habilidadesServicio;

    // --- LISTAR TODAS ---
    @GetMapping
    public List<Habilidades> getAll() {
        // Podrías mapear esto a DTOs también si prefieres no exponer IDs internos
        return habilidadesServicio.obtenerTodo();
    }

    // --- OBTENER UNA POR ID ---
    @GetMapping("/{id}")
    public Habilidades getById(@PathVariable Long id) {
        return habilidadesServicio.obtenerPorId(id);
    }

    // --- REGISTRAR NUEVA HABILIDAD ---
    @PostMapping("/registro")
    public Habilidades create(@RequestBody HabilidadesDTO dto) {
        // Cambié el nombre del parámetro a 'dto' para que sea consistente
        return habilidadesServicio.crear(dto);
    }

    // --- MODIFICAR HABILIDAD ---
    @PutMapping("/{id}")
    public Habilidades update(@PathVariable Long id, @RequestBody HabilidadesDTO dto) {
        return habilidadesServicio.modificar(id, dto);
    }

    // --- ELIMINAR HABILIDAD ---
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        if (habilidadesServicio.eliminar(id)) {
            return "La habilidad con ID " + id + " fue eliminada correctamente";
        } else {
            return "No se pudo encontrar la habilidad con ID " + id;
        }
    }
}
