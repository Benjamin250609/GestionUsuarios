package com.edutech.gestionusuario.service;

import com.edutech.gestionusuario.model.Estudiante;
import com.edutech.gestionusuario.model.Gerente;
import com.edutech.gestionusuario.repository.GerenteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class GerenteService {

    @Autowired
    private GerenteRepository gerenteRepository;

    public List<Gerente> findAll() {
        return gerenteRepository.findAll();
    }

    public Gerente findById(String run) {
        return gerenteRepository.findById(run).get();
    }

    public Gerente save(Gerente gerente) {
        List<Gerente> gerentes = gerenteRepository.findAll();
        for (Gerente gerente1 : gerentes) {
            if (gerente1.getCorreo().equals(gerente.getCorreo())) {
                return null;
            }
            else if (gerente1.getRun().equals(gerente.getRun())) {
                return null;
            }
        }
        return gerenteRepository.save(gerente);
    }

    public void delete(Gerente gerente) {
        gerenteRepository.delete(gerente);
    }

    public Gerente update(String id_gerente, Gerente gerente) {
        Gerente gerenteUpdate = gerenteRepository.findById(id_gerente)
                .orElseThrow(() -> new RuntimeException("Gerente no encontrado"));

        gerenteUpdate.setPnombre(gerente.getPnombre());
        gerenteUpdate.setSnombre(gerente.getSnombre());
        gerenteUpdate.setAppaterno(gerente.getAppaterno());
        gerenteUpdate.setApmaterno(gerente.getApmaterno());
        gerenteUpdate.setCorreo(gerente.getCorreo());
        gerenteUpdate.setDepartamento(gerente.getDepartamento());
        gerenteUpdate.setEstado(gerente.getEstado());

        return gerenteRepository.save(gerenteUpdate);
    }

    public String IniciarSesion(String correo, String contrasena){
        List<Gerente> gerentes = gerenteRepository.findAll();

        for (Gerente gerente : gerentes) {
            if (gerente.getCorreo().equals(correo) && gerente.getContrasena().equals(contrasena)) {
                return ("Inicio de sesión exitoso, bienvenido:" + gerente.getPnombre() + gerente.getAppaterno());
            }
        }
        return ("Correo o contraseña incorrectos, intente nuevamente.");
    }

    public String RecuperarContrasena(String run,String correo){
        Gerente gerente = gerenteRepository.findById(run).get();
        if(gerente.getCorreo().equals(correo)){
            return ("Se ha enviado un correo para recuperar la contraseña.");
        }else{
            return ("El correo ingresado no corresponde al usuario.");
        }
    }
}