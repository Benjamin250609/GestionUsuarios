package com.edutech.gestionusuario.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "estudiante")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Estudiante extends Usuario {

    @Column(length = 4)
    private float calificacion;

    @Column(length = 4)
    private float promCalificaciones;

    @Column(length = 2)
    private int porcAsistencia;

    @Column(nullable = false)
    private Integer idCurso;

}
