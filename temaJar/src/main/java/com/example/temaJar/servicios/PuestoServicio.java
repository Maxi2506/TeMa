package com.example.temaJar.servicios;

import com.example.temaJar.dtos.PuestoDTO;
import com.example.temaJar.enumeracion.Categoria;
import com.example.temaJar.enumeracion.NivelDeExperiencia;
import com.example.temaJar.models.Puesto;
import com.example.temaJar.repository.PuestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PuestoServicio {

    @Autowired
    private PuestoRepository puestoRepository;

    @Transactional(readOnly = true)
    public List<Puesto> obtenerTodo() {
        return puestoRepository.findAll().stream()
                .filter(Puesto::isActivo)
                .toList();
    }

    @Transactional(readOnly = true)
    public Puesto obtenerPorId(Long id) {
        return puestoRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public Puesto obtenerPorNombre(String nombre) {
        return puestoRepository.findByNombre(nombre).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Puesto> obtenerPorCategoria(String categoria) {
        String catLimpia = categoria.replace(" ", "_").toUpperCase();
        return puestoRepository.findByCategoria(catLimpia);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Puesto crear(PuestoDTO dto) {
        Puesto puesto = new Puesto();
        puesto.setNombre(dto.getNombre());
        puesto.setActivo(true);  // ← CORRECCIÓN: inicializar como activo

        if (dto.getCategoria() != null) {
            puesto.setCategoria(Categoria.valueOf(dto.getCategoria().replace(" ", "_").toUpperCase()));
        }
        if (dto.getNivel() != null) {
            puesto.setNivel(NivelDeExperiencia.valueOf(dto.getNivel().replace(" ", "_").toUpperCase()));
        }

        return puestoRepository.save(puesto);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Puesto modificar(Long id, PuestoDTO dto) {
        return puestoRepository.findById(id).map(puesto -> {
            puesto.setNombre(dto.getNombre());

            if (dto.getCategoria() != null) {
                puesto.setCategoria(Categoria.valueOf(dto.getCategoria().replace(" ", "_").toUpperCase()));
            }
            if (dto.getNivel() != null) {
                puesto.setNivel(NivelDeExperiencia.valueOf(dto.getNivel().replace(" ", "_").toUpperCase()));
            }

            return puestoRepository.save(puesto);
        }).orElseThrow(() -> new RuntimeException("No se encontró el puesto con ID: " + id));  // ← Unificado con UsuarioServicio
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public boolean eliminar(Long id) {
        return puestoRepository.findById(id).map(p -> {
            p.setActivo(false);
            puestoRepository.save(p);
            return true;
        }).orElse(false);
    }

    public long contar() {
        return puestoRepository.count();
    }

    @Transactional(readOnly = true)
    public List<Puesto> obtenerTodoSinFiltro() {
        return puestoRepository.findAll();
    }

}