package com.example.temaJar.controllers;

import com.example.temaJar.dtos.Cv_ArchivoDTO;
import com.example.temaJar.servicios.Cv_ArchivoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/cvarchivo")
public class Cv_ArchivoController {

    @Autowired
    private Cv_ArchivoServicio cvArchivoServicio;

    @PostMapping("/registro")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,
                                        @RequestParam("idUsuario") Long idUsuario) {
        try {
            Cv_ArchivoDTO resultado = cvArchivoServicio.procesarYGuardarArchivo(file, idUsuario);
            return new ResponseEntity<>(resultado, HttpStatus.CREATED);
        } catch (Exception e) {
            // Devolvemos el mensaje real para debuguear rápido en Postman
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping
    public List<Cv_ArchivoDTO> getAll() {
        return cvArchivoServicio.obtenerTodo();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cv_ArchivoDTO> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(cvArchivoServicio.obtenerPorId(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        if (cvArchivoServicio.eliminar(id)) {
            return ResponseEntity.ok("Archivo de CV eliminado correctamente");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Archivo no encontrado");
    }
}