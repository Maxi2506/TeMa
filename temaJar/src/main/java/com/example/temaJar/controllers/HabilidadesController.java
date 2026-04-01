package com.example.temaJar.controllers;

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
    public Habilidades create(@RequestBody Habilidades habilidades){
        return habilidadesServicio.crear(habilidades);
    }

    @PutMapping("/{id}")
    public Habilidades update(@PathVariable Long id, @RequestBody Habilidades habilidades) throws Exception{
        habilidades.setId(id);
        return habilidadesServicio.modificar(id, habilidades);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        if (habilidadesServicio.eliminar(id)){
            return "La habilidad con ID " + id + "eliminada correctamente";
        }else {
            return "La habilidad con ID" + id + "no encontrada";
        }
    }

}
