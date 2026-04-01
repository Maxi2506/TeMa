package com.example.temaJar.servicios;

import com.example.temaJar.models.Educacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.example.temaJar.repository.EducacionRepository;

import java.util.List;

@Service
public class EducacionServicio {

    @Autowired
    private EducacionRepository educacionRepository;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public List<Educacion> obtenerTodo(){
        return educacionRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Educacion obtenerPorId(Long id){
        return educacionRepository.findById(id).orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Educacion crear(Educacion educacion){
        return educacionRepository.save(educacion);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public boolean eliminar(Long id){
        if (educacionRepository.existsById(id)){
            educacionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Educacion modificar(Long id, Educacion educacionActualizado) throws Exception {
        return educacionRepository.findById(id).map(oficio -> {
            oficio.setInstitucion(educacionActualizado.getInstitucion());
            oficio.setTitulo(educacionActualizado.getTitulo());
            oficio.setDuracion(educacionActualizado.getDuracion());
            return educacionRepository.save(oficio);
        }).orElse(null);
    }

}
