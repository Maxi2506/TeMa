package com.example.temaJar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.temaJar.servicios.PaisServicio;
import java.util.List;
import com.example.temaJar.dtos.PaisDTO;

@RestController
@RequestMapping("/pais")
public class PaisController {

    @Autowired
    private PaisServicio paisServicio;

    @GetMapping
    public List<PaisDTO> getAll() {
        return paisServicio.obtenerTodo();
    }

    @GetMapping("/{id}")
    public PaisDTO getById(@PathVariable Long id) {
        return paisServicio.obtenerPorId(id);
    }

    @PostMapping("/registro")
    public PaisDTO create(@RequestBody PaisDTO paisDTO) {
        return paisServicio.crear(paisDTO);
    }

    @PutMapping("/{id}")
    public PaisDTO update(@PathVariable Long id, @RequestBody PaisDTO paisDTO) throws Exception {
        return paisServicio.modificar(id, paisDTO);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        if (paisServicio.eliminar(id)) {
            return "País con ID " + id + " eliminado correctamente.";
        } else {
            return "País con ID " + id + " no encontrado.";
        }
    }
}