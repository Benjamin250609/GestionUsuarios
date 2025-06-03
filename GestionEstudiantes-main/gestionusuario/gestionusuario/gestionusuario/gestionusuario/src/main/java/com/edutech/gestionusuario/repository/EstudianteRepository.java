package com.edutech.gestionusuario.repository;

import com.edutech.gestionusuario.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EstudianteRepository extends JpaRepository<Estudiante, String> {

    @Query("SELECT e FROM Estudiante e WHERE e.idCurso = ?1")
    public List<Estudiante> findAllByidCurso(Integer idCurso);
}
