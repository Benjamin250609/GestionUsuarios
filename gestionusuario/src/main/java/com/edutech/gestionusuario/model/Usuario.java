package com.edutech.gestionusuario.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Usuario {

    @Id
    @Column( length = 11, nullable = false)
    private String run;

    @Column(length = 50, nullable = false)
    private String pnombre;

    @Column(length = 50, nullable = true)
    private String snombre;

    @Column(length = 50, nullable = false)
    private String appaterno;

    @Column(length = 50, nullable = false)
    private String apmaterno;

    @Column(unique = true,length = 50, nullable = false)
    private String correo;

    @Column(length = 50, nullable = false)
    private String contrasena;

    @Column(length = 50, nullable = false)
    private String estado;
}
