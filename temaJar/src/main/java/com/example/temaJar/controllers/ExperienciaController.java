package com.example.temaJar.controllers;

import com.example.temaJar.dtos.ExperienciaDTO;
import com.example.temaJar.models.Experiencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.temaJar.servicios.ExperienciaServicio;

import java.util.List;

@RestController
@RequestMapping("/experiencia")
public class ExperienciaController {

    @Autowired
    private ExperienciaServicio experienciaServicio;

    @GetMapping
    public List<Experiencia> getAll(){
        return experienciaServicio.obtenerTodo();
    }

    @GetMapping("/{id}")
    public Experiencia getById(@PathVariable Long id){
        return experienciaServicio.obtenerPorId(id);
    }

    @PostMapping("/registro")
    public Experiencia create(@RequestBody ExperienciaDTO experiencia){
        return experienciaServicio.crear(experiencia);
    }

    @PutMapping("/{id}")
    public Experiencia update(@PathVariable Long id, @RequestBody ExperienciaDTO experiencia) throws Exception{
        experiencia.setId(id);
        return experienciaServicio.modificar(id, experiencia);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        if (experienciaServicio.eliminar(id)){
            return "La experiencia con ID " + id + "eliminada correctamente";
        }else {
            return "La experiencia con ID" + id + "no encontrada";
        }
    }

}
