package com.example.temaJar.servicios;

import com.example.temaJar.models.Cv_Habilidades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.example.temaJar.repository.Cv_HabilidadesRepository;

import java.util.List;

@Service
public class Cv_HabilidadesServicio {

    @Autowired
    private Cv_HabilidadesRepository cvHabilidadesRepository;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public List<Cv_Habilidades> obtenerTodo(){
        return cvHabilidadesRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Cv_Habilidades obtenerPorId(Long id){
        return cvHabilidadesRepository.findById(id).orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Cv_Habilidades crear(Cv_Habilidades cv_Habilidades){
        return cvHabilidadesRepository.save(cv_Habilidades);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public boolean eliminar(Long id){
        if (cvHabilidadesRepository.existsById(id)){
            cvHabilidadesRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Cv_Habilidades modificar(Long id, Cv_Habilidades cvHabilidadesActualizado) throws Exception {
        return cvHabilidadesRepository.findById(id).map(oficio -> {
            oficio.setHabilidades(cvHabilidadesActualizado.getHabilidades());
            return cvHabilidadesRepository.save(oficio);
        }).orElse(null);
    }

}
