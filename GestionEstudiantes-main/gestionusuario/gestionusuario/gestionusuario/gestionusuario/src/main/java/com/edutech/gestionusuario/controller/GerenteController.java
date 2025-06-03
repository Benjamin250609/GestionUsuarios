package com.edutech.gestionusuario.controller;

import com.edutech.gestionusuario.model.Gerente;
import com.edutech.gestionusuario.model.Instructor;
import com.edutech.gestionusuario.service.GerenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/gerentes")
public class GerenteController {

    @Autowired
    private GerenteService gerenteService;

    @GetMapping
    public ResponseEntity<List<Gerente>> ListarGerentes() {
        List<Gerente> gerentes = gerenteService.findAll();
        if (gerentes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(gerentes);
    }

    @GetMapping("/{run}")
    public ResponseEntity<Gerente> GerentePorRun(@PathVariable String run) {
        Gerente gerente = gerenteService.findById(run);
        if (gerente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(gerente);
    }

    @GetMapping("/InicioSesion/{correo}/{contrasena}")
    public ResponseEntity<String> IniciarSesion(@PathVariable String correo, @PathVariable String contrasena){
        String mensaje = gerenteService.IniciarSesion(correo, contrasena);
        return ResponseEntity.ok(mensaje);
    }

    @GetMapping("/RecuperarContrasena/{run}/{correo}")
    public ResponseEntity<String> RecuperarContrasena(@PathVariable String run, @PathVariable String correo){
        String mensaje = gerenteService.RecuperarContrasena(run, correo);
        return ResponseEntity.ok(mensaje);
    }

    @PostMapping("/{registrar}")
    public ResponseEntity<?> saveGerente(@RequestBody Gerente gerente) {
        Gerente gerenteGuardado = gerenteService.save(gerente);

        if (gerenteGuardado == null) {
            return ResponseEntity.badRequest()
                    .body("Error: El correo o RUN ya existe en el sistema");
        }

        return ResponseEntity.ok(gerenteGuardado);
    }


    @PutMapping("/actualizar/{run}")
    public ResponseEntity<?> updateGerente(@PathVariable String run, @RequestBody Gerente gerente) {
        return ResponseEntity.ok(gerenteService.update(run, gerente));
    }

    @DeleteMapping("/eliminar/{run}")
    public void deleteGerente(@PathVariable String run) {
        gerenteService.delete(gerenteService.findById(run));
    }
}