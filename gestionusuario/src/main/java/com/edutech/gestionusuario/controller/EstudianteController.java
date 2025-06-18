package com.edutech.gestionusuario.controller;

import com.edutech.gestionusuario.model.DTO.EstudianteDTO;
import com.edutech.gestionusuario.model.Estudiante;
import com.edutech.gestionusuario.service.EstudianteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import io.swagger.v3.oas.annotations.media.ExampleObject;

@RestController
@RequestMapping("/api/v1/estudiantes")
@Tag(name = "Estudiantes", description = "API de gestión de estudiantes")
public class EstudianteController {
    @Autowired
    private EstudianteService estudianteService;

    @Operation(summary = "Listar todos los estudiantes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de estudiantes encontrada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "array", implementation = Estudiante.class))),
            @ApiResponse(responseCode = "404", description = "No hay estudiantes registrados",
                    content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<Estudiante>> ListarEstudiantes() {
        List<Estudiante> estudiantes = estudianteService.findAll();
        if (estudiantes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }return ResponseEntity.ok(estudiantes);
    }

    @Operation(summary = "Obtener estudiante por RUN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudiante encontrado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Estudiante.class))),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado",
                    content = @Content)
    })
    @GetMapping("/{run}")
    public ResponseEntity<Estudiante> EstudiantePorRun(
            @Parameter(description = "RUN del estudiante", required = true, example = "12345678-9")
            @PathVariable String run) {
        Estudiante estudiante = estudianteService.findById(run);
        if (estudiante == null) {
            return ResponseEntity.notFound().build();
        }return ResponseEntity.ok(estudiante);
    }

    @Operation(summary = "Iniciar sesión de estudiante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas",
                    content = @Content)
    })
    @GetMapping("/InicioSesion/{correo}/{contrasena}")
    public ResponseEntity<String> IniciarSesion(
            @Parameter(description = "Correo del estudiante", required = true)
            @PathVariable String correo,
            @Parameter(description = "Contraseña del estudiante", required = true)
            @PathVariable String contrasena) {
        String mensaje = estudianteService.IniciarSesion(correo, contrasena);
        return ResponseEntity.ok(mensaje);
    }

    @Operation(summary = "Recuperar contraseña de estudiante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proceso de recuperación iniciado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado",
                    content = @Content)
    })
    @GetMapping("/RecuperarContrasena/{run}/{correo}")
    public ResponseEntity<String> RecuperarContrasena(
            @Parameter(description = "RUN del estudiante", required = true, example = "12345678-9")
            @PathVariable String run,
            @Parameter(description = "Correo del estudiante", required = true)
            @PathVariable String correo) {
        String mensaje = estudianteService.RecuperarContrasena(run, correo);
        return ResponseEntity.ok(mensaje);
    }

    @Operation(summary = "Obtener curso del estudiante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Curso encontrado exitosamente",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Curso no encontrado",
                    content = @Content)
    })
    @GetMapping("/obtenerCurso/{idCurso}")
    public ResponseEntity<?> ObtenerCursoEstudiante(
            @Parameter(description = "ID del curso", required = true, example = "1")
            @PathVariable Integer idCurso) {
        return ResponseEntity.ok(estudianteService.ObtenerCursosEstudiante(idCurso));
    }

    @Operation(summary = "Obtener DTO de estudiante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "DTO de estudiante encontrado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EstudianteDTO.class))),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado",
                    content = @Content)
    })
    @GetMapping("/EstudianteDTO/{run}")
    public ResponseEntity<EstudianteDTO> EstudianteDTO(
            @Parameter(description = "RUN del estudiante", required = true, example = "12345678-9")
            @PathVariable String run) {
        if (estudianteService.findById(run) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(estudianteService.obtenerEstudianteDTO(run));
    }

    @Operation(summary = "Registrar nuevo estudiante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudiante registrado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Estudiante.class))),
            @ApiResponse(responseCode = "400", description = "Error: El correo o RUN ya existe en el sistema",
                    content = @Content)
    })
    @PostMapping("/registrar")
    public ResponseEntity<?> saveEstudiante(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del nuevo estudiante",
                    required = true,
                    content = @Content(
                        schema = @Schema(implementation = Estudiante.class),
                        examples = @ExampleObject(value = """
                            {
                                "run": "12345678-9",
                                "pnombre": "Juan",
                                "snombre": "Pablo",
                                "appaterno": "Pérez",
                                "apmaterno": "González",
                                "correo": "juan.perez@ejemplo.com",
                                "contrasena": "contraseña123",
                                "estado": "ACTIVO",
                                "calificacion": 6.5,
                                "promCalificaciones": 6.0,
                                "porcAsistencia": 85,
                                "idCurso": 1
                            }
                            """)
                    )
            )
            @RequestBody Estudiante estudiante) {
        Estudiante estudianteGuardado = estudianteService.save(estudiante);

        if (estudianteGuardado == null) {
            return ResponseEntity.badRequest()
                    .body("Error: El correo o RUN ya existe en el sistema");
        }

        return ResponseEntity.ok(estudianteGuardado);
    }

    @Operation(summary = "Actualizar estudiante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudiante actualizado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Estudiante.class))),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado",
                    content = @Content)
})
@PutMapping("/actualizar/{run}")
public ResponseEntity<?> updateEstudiante(
        @Parameter(description = "RUN del estudiante", required = true, example = "12345678-9")
        @PathVariable String run,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos actualizados del estudiante",
                required = true,
                content = @Content(
                    schema = @Schema(implementation = Estudiante.class),
                    examples = @ExampleObject(value = """
                        {
                            "pnombre": "Juan",
                            "snombre": "Pablo",
                            "appaterno": "Pérez",
                            "apmaterno": "González",
                            "correo": "juan.perez@ejemplo.com",
                            "contrasena": "contraseña123",
                            "estado": "ACTIVO",
                            "calificacion": 6.5,
                            "promCalificaciones": 6.0,
                            "porcAsistencia": 85,
                            "idCurso": 1
                        }
                        """)
                )
        )
        @RequestBody Estudiante estudiante) {
    return ResponseEntity.ok(estudianteService.update(run,estudiante));
}

    @Operation(summary = "Eliminar estudiante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Estudiante eliminado exitosamente",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado",
                    content = @Content)
    })
    @DeleteMapping("/eliminar/{run}")
    public void deleteEstudiante(
            @Parameter(description = "RUN del estudiante", required = true, example = "12345678-9")
            @PathVariable String run) {
        estudianteService.delete(estudianteService.findById(run));
    }
}