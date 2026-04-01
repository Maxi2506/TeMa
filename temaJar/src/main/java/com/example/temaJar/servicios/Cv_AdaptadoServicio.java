package com.example.temaJar.servicios;

import com.example.temaJar.models.Cv_Adaptado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.example.temaJar.repository.Cv_AdaptadoRepository;

import java.util.List;

@Service
public class Cv_AdaptadoServicio {

    @Autowired
    private Cv_AdaptadoRepository cvAdaptadoRepository;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public List<Cv_Adaptado> obtenerTodo(){
        return cvAdaptadoRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Cv_Adaptado obtenerPorId(Long id){
        return cvAdaptadoRepository.findById(id).orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Cv_Adaptado crear(Cv_Adaptado cv_Adaptado){
        return cvAdaptadoRepository.save(cv_Adaptado);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public boolean eliminar(Long id){
        if (cvAdaptadoRepository.existsById(id)){
            cvAdaptadoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Cv_Adaptado modificar(Long id, Cv_Adaptado cvAdaptadoActualizado) throws Exception {
        return cvAdaptadoRepository.findById(id).map(oficio -> {
            oficio.setCv_base(cvAdaptadoActualizado.getCv_base());
            oficio.setPuesto(cvAdaptadoActualizado.getPuesto());
            oficio.setContenido(cvAdaptadoActualizado.getContenido());
            oficio.setFecha_create(cvAdaptadoActualizado.getFecha_create());
            return cvAdaptadoRepository.save(oficio);
        }).orElse(null);
    }

}
