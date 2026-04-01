package com.example.temaJar.controllers;

import com.example.temaJar.models.Localidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.temaJar.servicios.LocalidadServicio;

import java.util.List;

@RestController
@RequestMapping("/localidad")
public class LocalidadController {

    @Autowired
    private LocalidadServicio localidadServicio;

    // GET para obtener todas las localidad
    @GetMapping
    public List<Localidad> getAll() {
        return localidadServicio.obtenerTodo();
    }

    // GET para obtener una provincia por ID
    @GetMapping("/{id}")
    public Localidad getById(@PathVariable Long id) {
        return localidadServicio.obtenerPorId(id);
    }

    // POST para crear una nueva localidad
    @PostMapping("/registro")
    public Localidad create(@RequestBody Localidad localidad) {
        return localidadServicio.crear(localidad);
    }

    // PUT para actualizar una provincia existente
    @PutMapping("/{id}")
    public Localidad update(@PathVariable Long id, @RequestBody Localidad localidadActualizada) throws Exception{
        return localidadServicio.modificar(id, localidadActualizada) ;
    }

    // DELETE para eliminar una provincia por ID
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        if (localidadServicio.eliminar(id)) {
            return "Localidad con ID " + id + " eliminada correctamente.";
        } else {
            return "Localidad con ID " + id + " no encontrada.";
        }
    }

}
