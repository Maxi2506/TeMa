package com.example.temaJar.servicios;

import com.example.temaJar.dtos.ExperienciaDTO;
import com.example.temaJar.models.Cv_Base;
import com.example.temaJar.models.Experiencia;
import com.example.temaJar.models.Puesto;
import com.example.temaJar.repository.Cv_BaseRepository; // Verifica que el nombre del archivo sea exactamente este
import com.example.temaJar.repository.PuestoRepository;
import com.example.temaJar.repository.ExperienciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExperienciaServicio {

  @Autowired
  private ExperienciaRepository experienciaRepository; // Solo una vez

  @Autowired
  private PuestoRepository puestoRepository;

  @Autowired
  private Cv_BaseRepository cvBaseRepository;

  @Transactional(readOnly = true)
  public List<Experiencia> obtenerTodo(){
    return experienciaRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Experiencia obtenerPorId(Long id){
    return experienciaRepository.findById(id).orElse(null);
  }

  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
  public Experiencia crear(ExperienciaDTO dto){
    Experiencia experiencia = new Experiencia();
    experiencia.setEmpresa(dto.getEmpresa());
    experiencia.setFecha_ini(dto.getFechaIni());
    experiencia.setFecha_fin(dto.getFechaFin());

    Puesto puesto = puestoRepository.findByNombre(dto.getNombrePuesto()).orElse(null);
    experiencia.setPuesto(puesto);

    // Buscamos el CV base por el ID que viene en el DTO
    Cv_Base cv = cvBaseRepository.findById(dto.getIdCvBase()).orElse(null);
    experiencia.setId_cv(cv);

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
  public Experiencia modificar(Long id, ExperienciaDTO dto) throws Exception {
    return experienciaRepository.findById(id).map(experiencia -> {
      experiencia.setEmpresa(dto.getEmpresa());
      experiencia.setFecha_ini(dto.getFechaIni());
      experiencia.setFecha_fin(dto.getFechaFin());

      Puesto puesto = puestoRepository.findByNombre(dto.getNombrePuesto()).orElse(null);
      experiencia.setPuesto(puesto);

      return experienciaRepository.save(experiencia);
    }).orElse(null);
  }
}