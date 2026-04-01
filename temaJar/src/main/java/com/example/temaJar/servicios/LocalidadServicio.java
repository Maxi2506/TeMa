package com.example.temaJar.servicios;

import com.example.temaJar.models.Localidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.example.temaJar.repository.LocalidadRepository;

import java.util.List;

@Service
public class LocalidadServicio {

    @Autowired
    private LocalidadRepository localidadRepository;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public List<Localidad> obtenerTodo(){
        return localidadRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Localidad obtenerPorId(Long id){
        return localidadRepository.findById(id).orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Localidad crear(Localidad localidad){
        return localidadRepository.save(localidad);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public boolean eliminar(Long id){
        if (localidadRepository.existsById(id)){
            localidadRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Localidad modificar(Long id, Localidad localidadActualizado) throws Exception {
        return localidadRepository.findById(id).map(oficio -> {
            oficio.setNombre(localidadActualizado.getNombre());
            return localidadRepository.save(oficio);
        }).orElse(null);
    }

}
