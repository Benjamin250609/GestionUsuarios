package com.edutech.gestionusuario.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name= "instructor")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Instructor extends Usuario{

    @Column(length = 50, nullable = false)
    private String mencion;

    @Column(length = 2, nullable = false)
    private int porcAprobacion;


}
