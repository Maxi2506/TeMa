package com.example.temaJar.servicios;

import com.example.temaJar.dtos.EducacionDTO;
import com.example.temaJar.models.Cv_Base;
import com.example.temaJar.models.Educacion;
import com.example.temaJar.repository.Cv_BaseRepository;
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

    @Autowired
    private Cv_BaseRepository cvBaseRepository;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public List<Educacion> obtenerTodo(){
        return educacionRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Educacion obtenerPorId(Long id){
        return educacionRepository.findById(id).orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Educacion crear(EducacionDTO dto){
        Educacion educacion = new Educacion();
        educacion.setDuracion(dto.getDuracion());
        educacion.setTitulo(dto.getTitulo());
        educacion.setInstitucion(dto.getInstitucion());

        Cv_Base cv = cvBaseRepository.findById(dto.getIdCvBase()).orElse(null);
        educacion.setId_cv(cv);

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
    public Educacion modificar(Long id, EducacionDTO dto) throws Exception {
        return educacionRepository.findById(id).map(educacion -> {
            educacion.setInstitucion(dto.getInstitucion());
            educacion.setTitulo(dto.getTitulo());
            educacion.setDuracion(dto.getDuracion());

            Cv_Base cv = cvBaseRepository.findById(dto.getIdCvBase()).orElse(null);
            educacion.setId_cv(cv);

            return educacionRepository.save(educacion);
        }).orElse(null);
    }

}
