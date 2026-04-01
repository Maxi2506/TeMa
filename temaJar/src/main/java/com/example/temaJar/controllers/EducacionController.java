package com.example.temaJar.controllers;

import com.example.temaJar.models.Educacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.temaJar.servicios.EducacionServicio;

import java.util.List;

@RestController
@RequestMapping("/educacion")
public class EducacionController {

    @Autowired
    private EducacionServicio educacionServicio;

    @GetMapping
    public List<Educacion> getAll(){
        return educacionServicio.obtenerTodo();
    }

    @GetMapping("/{id}")
    public Educacion getById(@PathVariable Long id){
        return educacionServicio.obtenerPorId(id);
    }

    @PostMapping("/registro")
    public Educacion create(@RequestBody Educacion educacion){
        return educacionServicio.crear(educacion);
    }

    @PutMapping("/{id}")
    public Educacion update(@PathVariable Long id, @RequestBody Educacion educacion) throws Exception{
        educacion.setId(id);
        return educacionServicio.modificar(id, educacion);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        if (educacionServicio.eliminar(id)){
            return "La educación con ID " + id + "eliminada correctamente";
        }else {
            return "La educación con ID" + id + "no encontrada";
        }
    }

}
