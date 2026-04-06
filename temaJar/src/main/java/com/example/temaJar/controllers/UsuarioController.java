package com.example.temaJar.controllers;

import com.example.temaJar.dtos.UsuarioDTO;
import com.example.temaJar.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping
    public List<UsuarioDTO> getAll() {
        return usuarioServicio.obtenerTodo();
    }

    @GetMapping("/{id}")
    public UsuarioDTO getById(@PathVariable Long id) {
        return usuarioServicio.obtenerPorId(id);
    }

    @PostMapping("/registro")
    public UsuarioDTO create(@RequestBody UsuarioDTO usuarioDTO) {
        return usuarioServicio.crear(usuarioDTO);
    }

    @PutMapping("/{id}")
    public UsuarioDTO update(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        return usuarioServicio.modificar(id, usuarioDTO);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        if (usuarioServicio.eliminar(id)) {
            return "Usuario con ID " + id + " eliminado correctamente";
        } else {
            return "Usuario con ID " + id + " no encontrado";
        }
    }
}