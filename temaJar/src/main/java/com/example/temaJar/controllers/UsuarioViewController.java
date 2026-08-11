package com.example.temaJar.controllers;

import com.example.temaJar.dtos.UsuarioDTO;
import com.example.temaJar.servicios.LocalidadServicio;
import com.example.temaJar.servicios.PaisServicio;
import com.example.temaJar.servicios.ProvinciaServicio;
import com.example.temaJar.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession; // ✨ Importación necesaria para manejar la sesión
import java.util.ArrayList;

@Controller
@RequestMapping("/vistas")
public class UsuarioViewController {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private PaisServicio paisServicio;

    @Autowired
    private ProvinciaServicio provinciaServicio;

    @Autowired
    private LocalidadServicio localidadServicio;

    // 1. RUTA DE REGISTRO DE USUARIO
    @GetMapping("/registro-usuario")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new UsuarioDTO());

        // Carga de Países
        try {
            model.addAttribute("listaPaises", paisServicio.obtenerTodo());
        } catch (Exception e) {
            System.out.println("❌ ¡EL SERVICIO DE PAÍSES ROMPIÓ EN JAVA!");
            e.printStackTrace();
            model.addAttribute("listaPaises", new ArrayList<>());
        }

        // Carga y Diagnóstico de Provincias
        try {
            var provincias = provinciaServicio.obtenerTodo();
            System.out.println("====== DIAGNÓSTICO DE PROVINCIAS ======");
            if (provincias != null) {
                System.out.println("👉 Cantidad de provincias encontradas: " + provincias.size());
            }
            model.addAttribute("listaProvincias", provincias);
        } catch (Exception e) {
            System.out.println("❌ ¡EL SERVICIO DE PROVINCIAS ROMPIÓ EN JAVA!");
            e.printStackTrace();
            model.addAttribute("listaProvincias", new ArrayList<>());
        }

        // Carga y Diagnóstico de Localidades
        try {
            var localidades = localidadServicio.obtenerTodo();
            System.out.println("====== DIAGNÓSTICO DE LOCALIDADES ======");
            if (localidades != null) {
                System.out.println("👉 Cantidad de localidades encontradas: " + localidades.size());
            }
            model.addAttribute("listaLocalidades", localidades);
        } catch (Exception e) {
            System.out.println("❌ ¡EL SERVICIO DE LOCALIDADES ROMPIÓ EN JAVA!");
            e.printStackTrace();
            model.addAttribute("listaLocalidades", new ArrayList<>());
        }

        return "registroUsuario";
    }

    // 2. RUTA PARA EL LOGIN
    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    // 3. RUTA PARA LA CARGA DEL CV (Actualizada)
    @GetMapping("/carga-cv")
    public String mostrarCargaCv(Model model, HttpSession session) {
        Object usuarioLogueado = session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            return "redirect:/vistas/login";
        }

        model.addAttribute("usuario", usuarioLogueado);

        // 🔥 Cambiamos "carga-cv" por "cargaCv" para que coincida con tu archivo
        return "carga-cv";
    }

    // 4. ✨ NUEVA RUTA: PANEL PRINCIPAL (INDEX)
    @GetMapping("/index")
    public String mostrarIndex(Model model, HttpSession session) {
        // Recuperamos el objeto usuario guardado en la sesión durante el login exitoso
        Object usuarioLogueado = session.getAttribute("usuarioLogueado");

        // Si el usuario no está logueado (sesión nula o expirada), lo redirigimos al login
        if (usuarioLogueado == null) {
            return "redirect:/vistas/login";
        }

        // Enviamos los datos del usuario al HTML index.html
        model.addAttribute("usuario", usuarioLogueado);

        return "index";
    }
}
