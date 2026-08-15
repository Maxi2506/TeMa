package com.example.temaJar.controllers;

import com.example.temaJar.dtos.PuestoDTO;
import com.example.temaJar.dtos.UsuarioDTO;
import com.example.temaJar.enumeracion.Categoria;
import com.example.temaJar.enumeracion.NivelDeExperiencia;
import com.example.temaJar.models.Puesto;
import com.example.temaJar.servicios.PuestoServicio;
import com.example.temaJar.servicios.UsuarioServicio;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vistas")
public class AdminViewController {

    @Autowired
    private PuestoServicio puestoService;

    @Autowired
    private UsuarioServicio usuarioService;

    // ═══════════════════════════════════════
    // ROLES (PUESTOS)
    // ═══════════════════════════════════════

    @GetMapping("/roles")
    public String roles(Model model, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/vistas/login";
        model.addAttribute("usuario", obtenerUsuarioDeSesion(session));
        model.addAttribute("roles", puestoService.obtenerTodo());
        return "roles";
    }

    @GetMapping("/roles/nuevo")
    public String nuevoRol(Model model, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/vistas/login";
        model.addAttribute("usuario", obtenerUsuarioDeSesion(session));
        model.addAttribute("rol", new Puesto());
        model.addAttribute("categorias", Categoria.values());
        model.addAttribute("niveles", NivelDeExperiencia.values());
        model.addAttribute("modo", "crear");
        return "rol-form";
    }

    @GetMapping("/roles/editar/{id}")
    public String editarRol(@PathVariable Long id, Model model, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/vistas/login";

        Puesto rol = puestoService.obtenerPorId(id);
        if (rol == null) return "redirect:/vistas/roles";

        model.addAttribute("usuario", obtenerUsuarioDeSesion(session));
        model.addAttribute("rol", rol);
        model.addAttribute("categorias", Categoria.values());
        model.addAttribute("niveles", NivelDeExperiencia.values());
        model.addAttribute("modo", "editar");
        return "rol-form";
    }

    @PostMapping("/roles/guardar")
    public String guardarRol(@ModelAttribute PuestoDTO rolDto,
                             @RequestParam(required = false) Long id,
                             HttpSession session) {
        if (!esAdmin(session)) return "redirect:/vistas/login";

        if (id != null) {
            puestoService.modificar(id, rolDto);
        } else {
            puestoService.crear(rolDto);
        }
        return "redirect:/vistas/roles";
    }

    @GetMapping("/roles/eliminar/{id}")
    public String eliminarRol(@PathVariable Long id, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/vistas/login";
        puestoService.eliminar(id);
        return "redirect:/vistas/roles";
    }

    // ═══════════════════════════════════════
    // USUARIOS
    // ═══════════════════════════════════════

    @GetMapping("/usuarios")
    public String usuarios(Model model, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/vistas/login";
        model.addAttribute("usuario", obtenerUsuarioDeSesion(session));
        model.addAttribute("usuarios",
                usuarioService.obtenerTodo().stream()
                        .filter(dto -> "USUARIO".equals(dto.getRol()))
                        .toList()
        );
        return "usuarios";
    }

    @GetMapping("/usuarios/editar/{id}")
    public String editarUsuario(@PathVariable Long id, Model model, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/vistas/login";

        UsuarioDTO u = usuarioService.obtenerPorId(id);
        if (u == null) return "redirect:/vistas/usuarios";

        model.addAttribute("usuario", obtenerUsuarioDeSesion(session));
        model.addAttribute("usuarioEditar", u);
        return "usuario-form";
    }

    @PostMapping("/usuarios/guardar")
    public String guardarUsuario(@ModelAttribute UsuarioDTO dto,
                                 @RequestParam Long id,
                                 HttpSession session) {
        if (!esAdmin(session)) return "redirect:/vistas/login";

        usuarioService.modificar(id, dto);
        return "redirect:/vistas/usuarios";
    }

    @GetMapping("/usuarios/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/vistas/login";
        usuarioService.eliminar(id);
        return "redirect:/vistas/usuarios";
    }

    // ═══════════════════════════════════════
    // ESTADÍSTICAS
    // ═══════════════════════════════════════

    @GetMapping("/estadisticas")
    public String estadisticas(Model model, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/vistas/login";
        model.addAttribute("usuario", obtenerUsuarioDeSesion(session));
        return "estadisticas";
    }

    // ═══════════════════════════════════════
    // AUXILIARES
    // ═══════════════════════════════════════

    private UsuarioDTO obtenerUsuarioDeSesion(HttpSession session) {
        Object obj = session.getAttribute("usuarioLogueado");
        return (obj instanceof UsuarioDTO) ? (UsuarioDTO) obj : null;
    }

    private boolean esAdmin(HttpSession session) {
        UsuarioDTO u = obtenerUsuarioDeSesion(session);
        return u != null && "ADMIN".equals(u.getRol());
    }
}