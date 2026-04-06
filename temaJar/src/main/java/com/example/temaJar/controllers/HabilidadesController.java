package com.example.temaJar.controllers;

import com.example.temaJar.dtos.HabilidadesDTO;
import com.example.temaJar.models.Habilidades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.temaJar.servicios.HabilidadesServicio;

import java.util.List;

@RestController
@RequestMapping("/habilidades")
public class HabilidadesController {

    @Autowired
    private HabilidadesServicio habilidadesServicio;

    @GetMapping
    public List<Habilidades> getAll(){
        return habilidadesServicio.obtenerTodo();
    }

    @GetMapping("/{id}")
    public Habilidades getById(@PathVariable Long id){
        return habilidadesServicio.obtenerPorId(id);
    }

    @PostMapping("/registro")
    public Habilidades create(@RequestBody HabilidadesDTO habilidades){
        return habilidadesServicio.crear(habilidades);
    }

    @PutMapping("/{id}")
    public Habilidades update(@PathVariable Long id, @RequestBody HabilidadesDTO dto) {
        // Ya no necesitas setear el ID manualmente al objeto, el servicio lo busca por @PathVariable
        return habilidadesServicio.modificar(id, dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        if (habilidadesServicio.eliminar(id)){
            return "La habilidad con ID " + id + " eliminada correctamente"; // Espacio agregado
        } else {
            return "La habilidad con ID " + id + " no encontrada"; // Espacio agregado
        }
    }

}
