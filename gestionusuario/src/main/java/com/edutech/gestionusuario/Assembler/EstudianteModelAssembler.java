package com.edutech.gestionusuario.Assembler;

import com.edutech.gestionusuario.controller.EstudianteControllerV2;
import com.edutech.gestionusuario.model.Estudiante;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EstudianteModelAssembler implements RepresentationModelAssembler<Estudiante, EntityModel<Estudiante>> {

    @Override
    public EntityModel<Estudiante> toModel(Estudiante estudiante) {
        return EntityModel.of(estudiante,
                linkTo(methodOn(EstudianteControllerV2.class).EstudiantePorRun(estudiante.getRun())).withSelfRel(),
                linkTo(methodOn(EstudianteControllerV2.class).ListarEstudiantes()).withRel("estudiantes"));
    }
}