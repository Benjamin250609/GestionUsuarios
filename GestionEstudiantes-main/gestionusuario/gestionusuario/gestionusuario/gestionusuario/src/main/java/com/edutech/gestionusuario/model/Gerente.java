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
@Table(name = "gerente")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Gerente extends Usuario {

    @Column(length = 50, nullable = false)
    private String departamento;

}
