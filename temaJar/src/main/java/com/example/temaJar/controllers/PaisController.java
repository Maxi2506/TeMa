package com.example.temaJar.controllers;

import com.example.temaJar.models.Pais;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.temaJar.servicios.PaisServicio;

import java.util.List;

@RestController
@RequestMapping("/pais")
public class PaisController {

    @Autowired
    private PaisServicio paisServicio;

    // GET para obtener todas los paises
    @GetMapping
    public List<Pais> getAll() {
        return paisServicio.obtenerTodo();
    }

    // GET para obtener un País por ID
    @GetMapping("/{id}")
    public Pais getById(@PathVariable Long id) {
        return paisServicio.obtenerPorId(id);
    }

    // POST para crear un nuevo pais
    @PostMapping("/registro")
    public Pais create(@RequestBody Pais pais) {
        return paisServicio.crear(pais);
    }

    // PUT para actualizar un país existente
    @PutMapping("/{id}")
    public Pais update(@PathVariable Long id, @RequestBody Pais paisActualizada) throws Exception{
        return paisServicio.modificar(id, paisActualizada) ;
    }

    // DELETE para eliminar una provincia por ID
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        if (paisServicio.eliminar(id)) {
            return "Localidad con ID " + id + " eliminada correctamente.";
        } else {
            return "Localidad con ID " + id + " no encontrada.";
        }
    }

}