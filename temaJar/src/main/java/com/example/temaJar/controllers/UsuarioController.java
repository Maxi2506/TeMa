package com.example.temaJar.controllers;

import com.example.temaJar.dtos.UsuarioDTO; // Asegúrate de importar tu DTO
import com.example.temaJar.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.temaJar.servicios.UsuarioServicio;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping
    public List<Usuario> getAll(){
        return usuarioServicio.obtenerTodo();
    }

    @GetMapping("/{id}")
    public Usuario getById(@PathVariable Long id){
        return usuarioServicio.obtenerPorId(id);
    }

    // CORRECCION: Ahora recibe el DTO que contiene todo (datos + nombres de ubicación)
    @PostMapping("/registro")
    public Usuario create(@RequestBody UsuarioDTO usuarioDTO){
        return usuarioServicio.crearDesdeDTO(usuarioDTO);
    }

    // CORRECCIÓN: El PutMapping también debe usar el DTO
    @PutMapping("/{id}")
    public Usuario update(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO){
        return usuarioServicio.actualizarDesdeDTO(id, usuarioDTO);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        if (usuarioServicio.eliminar(id)){
            return "El cliente con ID " + id + " eliminado correctamente";
        } else {
            return "El cliente con ID " + id + " no encontrado";
        }
    }
}