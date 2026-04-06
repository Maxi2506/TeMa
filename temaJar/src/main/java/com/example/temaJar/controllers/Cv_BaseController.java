package com.example.temaJar.controllers;

import com.example.temaJar.dtos.Cv_BaseDTO;
import com.example.temaJar.servicios.Cv_BaseServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cvbase")
public class Cv_BaseController {

    @Autowired
    private Cv_BaseServicio cvBaseServicio;

    @GetMapping
    public List<Cv_BaseDTO> getAll() {
        return cvBaseServicio.obtenerTodo();
    }

    @GetMapping("/{id}")
    public Cv_BaseDTO getById(@PathVariable Long id) {
        return cvBaseServicio.obtenerPorId(id);
    }

    @PostMapping("/registro")
    public Cv_BaseDTO create(@RequestBody Cv_BaseDTO cvBase) {
        return cvBaseServicio.crear(cvBase);
    }

    @PutMapping("/{id}")
    public Cv_BaseDTO update(@PathVariable Long id, @RequestBody Cv_BaseDTO cvBase) throws Exception {
        return cvBaseServicio.modificar(id, cvBase);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        if (cvBaseServicio.eliminar(id)) {
            return "CV Base con ID " + id + " eliminado correctamente";
        } else {
            return "CV Base con ID " + id + " no encontrado";
        }
    }
}
