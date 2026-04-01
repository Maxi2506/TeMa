package com.example.temaJar.servicios;

import com.example.temaJar.models.Pais;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.example.temaJar.repository.PaisRepository;

import java.util.List;

@Service
public class PaisServicio {

    @Autowired
    private PaisRepository paisRepository;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public List<Pais> obtenerTodo(){
        return paisRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Pais obtenerPorId(Long id){
        return paisRepository.findById(id).orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Pais crear(Pais pais){
        return paisRepository.save(pais);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public boolean eliminar(Long id){
        if (paisRepository.existsById(id)){
            paisRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Pais modificar(Long id, Pais paisActualizado) throws Exception {
        return paisRepository.findById(id).map(oficio -> {
            oficio.setNombre(paisActualizado.getNombre());
            return paisRepository.save(oficio);
        }).orElse(null);
    }

}