package com.example.temaJar.servicios;

import com.example.temaJar.models.Cv_Archivo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.example.temaJar.repository.Cv_ArchivoRepository;

import java.util.List;

@Service
public class Cv_ArchivoServicio {

    @Autowired
    private Cv_ArchivoRepository cvArchivoRepository;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public List<Cv_Archivo> obtenerTodo(){
        return cvArchivoRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Cv_Archivo obtenerPorId(Long id){
        return cvArchivoRepository.findById(id).orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Cv_Archivo crear(Cv_Archivo cv_Archivo){
        return cvArchivoRepository.save(cv_Archivo);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public boolean eliminar(Long id){
        if (cvArchivoRepository.existsById(id)){
            cvArchivoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Cv_Archivo modificar(Long id, Cv_Archivo cvArchivoActualizado) throws Exception {
        return cvArchivoRepository.findById(id).map(oficio -> {
            oficio.setNombre_usuario(cvArchivoActualizado.getNombre_usuario());
            oficio.setNombre_archivo(cvArchivoActualizado.getNombre_archivo());
            oficio.setFecha_carga(cvArchivoActualizado.getFecha_carga());
            oficio.setTexto_extraido(cvArchivoActualizado.getTexto_extraido());
            return cvArchivoRepository.save(oficio);
        }).orElse(null);
    }

}
