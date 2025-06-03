package com.edutech.gestionusuario.controller;

import com.edutech.gestionusuario.model.DTO.InstructorDTO;
import com.edutech.gestionusuario.model.Instructor;
import com.edutech.gestionusuario.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/instructores")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @GetMapping
    public ResponseEntity<List<Instructor>> ListarInstructores() {
        List<Instructor> instructores = instructorService.findAll();
        if (instructores.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(instructores);
    }

    @GetMapping("/{run}")
    public ResponseEntity<InstructorDTO> InstructorPorRun(@PathVariable String run) {
        if (instructorService.findById(run) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(instructorService.ObtenerInstructorDto(run));
    }

    @GetMapping("/InicioSesion/{correo}/{contrasena}")
    public ResponseEntity<String> IniciarSesion(@PathVariable String correo, @PathVariable String contrasena){
        String mensaje = instructorService.IniciarSesion(correo, contrasena);
        return ResponseEntity.ok(mensaje);
    }

    @GetMapping("/RecuperarContrasena/{run}/{correo}")
    public ResponseEntity<String> RecuperarContrasena(@PathVariable String run, @PathVariable String correo){
        String mensaje = instructorService.RecuperarContrasena(run, correo);
        return ResponseEntity.ok(mensaje);
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> saveInstructor(@RequestBody Instructor instructor) {
        Instructor instructorGuardado = instructorService.save(instructor);
        
        if (instructorGuardado == null) {
            return ResponseEntity.badRequest()
                    .body("Error: El correo o RUN ya existe en el sistema");
        }
        
        return ResponseEntity.ok(instructorGuardado);
    }

    @PutMapping("/actualizar/{run}")
    public ResponseEntity<?> updateInstructor(@PathVariable String run, @RequestBody Instructor instructor) {
        return ResponseEntity.ok(instructorService.update(run, instructor));
    }

    @DeleteMapping("/eliminar/{run}")
    public void deleteInstructor(@PathVariable String run) {
        instructorService.delete(instructorService.findById(run));
    }
}