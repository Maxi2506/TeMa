package com.example.temaJar.controllers;

import com.example.temaJar.models.Provincia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.temaJar.servicios.ProvinciaServicio;

import java.util.List;

@RestController
@RequestMapping("/provincia")
public class ProvinciaController {

    @Autowired
    private ProvinciaServicio provinciaServicio;

    // GET para obtener todas las provincias
    @GetMapping
    public List<Provincia> getAll() {
        return provinciaServicio.obtenerTodo();
    }

    // GET para obtener una provincia por ID
    @GetMapping("/{id}")
    public Provincia getById(@PathVariable Long id) {
        return provinciaServicio.obtenerPorId(id);
    }

    // POST para crear una nueva provincia
    @PostMapping("/registro")
    public Provincia create(@RequestBody Provincia provincia) {
        return provinciaServicio.crear(provincia);
    }

    // PUT para actualizar una provincia existente
    @PutMapping("/{id}")
    public Provincia update(@PathVariable Long id, @RequestBody Provincia provinciaActualizada) throws Exception{
        return provinciaServicio.modificar(id, provinciaActualizada) ;
    }

    // DELETE para eliminar una provincia por ID
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        if (provinciaServicio.eliminar(id)) {
            return "Provincia con ID " + id + " eliminada correctamente.";
        } else {
            return "Provincia con ID " + id + " no encontrada.";
        }
    }

}
