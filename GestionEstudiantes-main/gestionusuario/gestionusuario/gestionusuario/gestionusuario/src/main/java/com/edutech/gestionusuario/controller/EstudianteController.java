package com.edutech.gestionusuario.controller;

import com.edutech.gestionusuario.model.DTO.EstudianteDTO;
import com.edutech.gestionusuario.model.Estudiante;
import com.edutech.gestionusuario.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/estudiantes")
public class EstudianteController {
    @Autowired
    private EstudianteService estudianteService;

    @GetMapping
    public ResponseEntity<List<Estudiante>> ListarEstudiantes() {
        List<Estudiante> estudiantes = estudianteService.findAll();
        if (estudiantes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }return ResponseEntity.ok(estudiantes);
    }

    @GetMapping("/{run}")
    public ResponseEntity<Estudiante> EstudiantePorRun(@PathVariable String run) {
        Estudiante estudiante = estudianteService.findById(run);
        if (estudiante == null) {
            return ResponseEntity.notFound().build();
        }return ResponseEntity.ok(estudiante);
    }

    @GetMapping("/InicioSesion/{correo}/{contrasena}")
    public ResponseEntity<String> IniciarSesion(@PathVariable String correo, @PathVariable String contrasena){
        String mensaje = estudianteService.IniciarSesion(correo, contrasena);
        return ResponseEntity.ok(mensaje);
    }

    @GetMapping("/RecuperarContrasena/{run}/{correo}")
    public ResponseEntity<String> RecuperarContrasena(@PathVariable String run, @PathVariable String correo){
        String mensaje = estudianteService.RecuperarContrasena(run, correo);
        return ResponseEntity.ok(mensaje);
    }

    @GetMapping("/obtenerCurso/{idCurso}")
    public ResponseEntity<?> ObtenerCursoEstudiante(@PathVariable Integer idCurso) {
        return ResponseEntity.ok(estudianteService.ObtenerCursosEstudiante(idCurso));
    }

    @GetMapping("/EstudianteDTO/{run}")
    public ResponseEntity<EstudianteDTO> EstudianteDTO(@PathVariable String run){
        if (estudianteService.findById(run) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(estudianteService.obtenerEstudianteDTO(run));
    }


    @PostMapping("/registrar")
    public ResponseEntity<?> saveEstudiante(@RequestBody Estudiante estudiante) {
        Estudiante estudianteGuardado = estudianteService.save(estudiante);
        
        if (estudianteGuardado == null) {
            return ResponseEntity.badRequest()
                .body("Error: El correo o RUN ya existe en el sistema");
        }
        
        return ResponseEntity.ok(estudianteGuardado);
    }

    @PutMapping("/actualizar/{run}")
    public ResponseEntity<?> updateEstudiante(@PathVariable String run, @RequestBody Estudiante estudiante) {
        return ResponseEntity.ok(estudianteService.update(run,estudiante));
    }

    @DeleteMapping("/eliminar/{run}")
    public void deleteEstudiante(@PathVariable String run) {
        estudianteService.delete(estudianteService.findById(run));
    }













}