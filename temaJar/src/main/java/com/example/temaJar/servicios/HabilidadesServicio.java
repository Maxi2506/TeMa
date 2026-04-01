package com.example.temaJar.servicios;

import com.example.temaJar.models.Habilidades;
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

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public List<Habilidades> obtenerTodo(){
        return habilidadesRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Habilidades obtenerPorId(Long id){
        return habilidadesRepository.findById(id).orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Habilidades crear(Habilidades habilidades){
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
    public Habilidades modificar(Long id, Habilidades habilidadesActualizado) throws Exception {
        return habilidadesRepository.findById(id).map(oficio -> {
            oficio.setNombre(habilidadesActualizado.getNombre());
            return habilidadesRepository.save(oficio);
        }).orElse(null);
    }

}
