package com.example.temaJar.servicios;

import com.example.temaJar.models.Experiencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.example.temaJar.repository.ExperienciaRepository;

import java.util.List;

@Service
public class ExperienciaServicio {

  @Autowired
  private ExperienciaRepository experienciaRepository;

  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
  public List<Experiencia> obtenerTodo(){
    return experienciaRepository.findAll();
  }

  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
  public Experiencia obtenerPorId(Long id){
    return experienciaRepository.findById(id).orElse(null);
  }

  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
  public Experiencia crear(Experiencia experiencia){
    return experienciaRepository.save(experiencia);
  }

  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
  public boolean eliminar(Long id){
    if (experienciaRepository.existsById(id)){
      experienciaRepository.deleteById(id);
      return true;
    }
    return false;
  }

  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
  public Experiencia modificar(Long id, Experiencia ExpActualizada) throws Exception {
    return experienciaRepository.findById(id).map(oficio -> {
      oficio.setEmpresa(ExpActualizada.getEmpresa());
      oficio.setPuesto(ExpActualizada.getPuesto());
      oficio.setFecha_ini(ExpActualizada.getFecha_ini());
      oficio.setFecha_fin(ExpActualizada.getFecha_fin());
      return experienciaRepository.save(oficio);
    }).orElse(null);
  }

}