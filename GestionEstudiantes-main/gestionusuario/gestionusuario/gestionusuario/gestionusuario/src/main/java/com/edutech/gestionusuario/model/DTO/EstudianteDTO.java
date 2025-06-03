package com.edutech.gestionusuario.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteDTO {

    private String run;

    private String pnombre;

    private String appaterno;

    private String correo;

    private String idcurso;
}
