package com.edutech.gestionusuario.repository;

import com.edutech.gestionusuario.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<Instructor, String> {
}
