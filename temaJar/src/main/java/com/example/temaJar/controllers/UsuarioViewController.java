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

    @GetMapping("/registro-usuario")
    public String mostrarFormularioRegistro(Model model) {
        // Objeto para capturar los datos del formulario
        model.addAttribute("usuario", new UsuarioDTO());

        // Cargamos las listas de la base de datos para los <select>
        model.addAttribute("listaPaises", paisServicio.obtenerTodo());
        model.addAttribute("listaProvincias", provinciaServicio.obtenerTodo());
        model.addAttribute("listaLocalidades", localidadServicio.obtenerTodo());

        return "index"; // O "registro_usuario" según tu archivo
    }
}