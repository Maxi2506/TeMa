package com.example.temaJar.servicios;

import com.example.temaJar.dtos.HabilidadesDTO;
import com.example.temaJar.enumeracion.Categoria;
import com.example.temaJar.enumeracion.NivelDeExperiencia;
import com.example.temaJar.models.Habilidades;
import com.example.temaJar.models.Puesto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.example.temaJar.repository.HabilidadesRepository;

import java.util.List;

@Service
public class HabilidadesServicio {

    @Autowired
    private HabilidadesRepository habilidadesRepository;

    @Transactional(readOnly = true)
    public Habilidades obtenerPorNombre(String nombre) {
        return habilidadesRepository.findByNombre(nombre).orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public List<Habilidades> obtenerTodo(){
        return habilidadesRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Habilidades obtenerPorId(Long id){
        return habilidadesRepository.findById(id).orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Habilidades crear(HabilidadesDTO dto) {
        Habilidades habilidades = new Habilidades();
        habilidades.setNombre(dto.getNombre());
        return habilidadesRepository.save(habilidades);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public boolean eliminar(Long id){
        if (habilidadesRepository.existsById(id)){
            habilidadesRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Habilidades modificar(Long id, HabilidadesDTO dto) {
        return habilidadesRepository.findById(id).map(habilidad -> {
            // Usamos el DTO para actualizar
            habilidad.setNombre(dto.getNombre().trim());
            return habilidadesRepository.save(habilidad);
        }).orElse(null);
    }

}
