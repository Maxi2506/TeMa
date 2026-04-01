package com.example.temaJar.servicios;

import com.example.temaJar.models.Cv_Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.example.temaJar.repository.Cv_BaseRepository;

import java.util.List;

@Service
public class Cv_BaseServicio {

    @Autowired
    private Cv_BaseRepository cvBaseRepository;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public List<Cv_Base> obtenerTodo(){
        return cvBaseRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Cv_Base obtenerPorId(Long id){
        return cvBaseRepository.findById(id).orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Cv_Base crear(Cv_Base cv_Base){
        return cvBaseRepository.save(cv_Base);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public boolean eliminar(Long id){
        if (cvBaseRepository.existsById(id)){
            cvBaseRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Cv_Base modificar(Long id, Cv_Base cvBaseActualizado) throws Exception {
        return cvBaseRepository.findById(id).map(oficio -> {
            oficio.setNombre_usuario(cvBaseActualizado.getNombre_usuario());
            oficio.setResumen_profesional(cvBaseActualizado.getResumen_profesional());
            oficio.setTelefono(cvBaseActualizado.getTelefono());
            oficio.setDomicilio(cvBaseActualizado.getDomicilio());
            oficio.setLinkedin_url(cvBaseActualizado.getLinkedin_url());
            oficio.setLocalidad(cvBaseActualizado.getLocalidad());
            oficio.setProvincia(cvBaseActualizado.getProvincia());
            oficio.setPais(cvBaseActualizado.getPais());
            return cvBaseRepository.save(oficio);
        }).orElse(null);
    }

}
