package com.example.temaJar.servicios;

import com.example.temaJar.models.Provincia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.example.temaJar.repository.ProvinciaRepository;

import java.util.List;

@Service
public class ProvinciaServicio {

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public List<Provincia> obtenerTodo(){
        return provinciaRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Provincia obtenerPorId(Long id){
        return provinciaRepository.findById(id).orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Provincia crear(Provincia provincia){
        return provinciaRepository.save(provincia);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public boolean eliminar(Long id){
        if (provinciaRepository.existsById(id)){
            provinciaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Provincia modificar(Long id, Provincia provinciaActualizada) throws Exception {
        return provinciaRepository.findById(id).map(oficio -> {
            oficio.setNombre(provinciaActualizada.getNombre());
            return provinciaRepository.save(oficio);
        }).orElse(null);
    }

}
