package com.example.temaJar.controllers;

import com.example.temaJar.dtos.UsuarioDTO;
import com.example.temaJar.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
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

    // ✨ ENDPOINT DE LOGIN (Reutilizando UsuarioDTO)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioDTO loginDatos, HttpSession session) {
        try {
            // Validamos las credenciales usando el servicio
            UsuarioDTO usuario = usuarioServicio.login(loginDatos.getCorreo(), loginDatos.getClave());

            // Guardamos el usuario validado en la sesión HTTP
            session.setAttribute("usuarioLogueado", usuario);

            // Retornamos 200 OK para que el frontend sepa que el inicio de sesión fue exitoso
            return ResponseEntity.ok(usuario);

        } catch (Exception e) {
            // Si las credenciales fallan o el usuario no existe, enviamos un 401 Unauthorized
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping("/debug/todos")
    public List<UsuarioDTO> getAllDebug() {
        return usuarioServicio.obtenerTodoSinFiltro();
    }

}