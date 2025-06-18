package com.edutech.gestionusuario.controller;

import com.edutech.gestionusuario.model.DTO.InstructorDTO;
import com.edutech.gestionusuario.model.Instructor;
import com.edutech.gestionusuario.service.InstructorService;
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
@RequestMapping("/api/v1/instructores")
@Tag(name = "Instructores", description = "API de gestión de instructores")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @Operation(summary = "Listar todos los instructores")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de instructores encontrada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "array", implementation = Instructor.class))),
        @ApiResponse(responseCode = "404", description = "No hay instructores registrados",
                    content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<Instructor>> ListarInstructores() {
        List<Instructor> instructores = instructorService.findAll();
        if (instructores.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(instructores);
    }

    @Operation(summary = "Obtener instructor por RUN")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Instructor encontrado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InstructorDTO.class))),
        @ApiResponse(responseCode = "404", description = "Instructor no encontrado",
                    content = @Content)
    })
    @GetMapping("/{run}")
    public ResponseEntity<InstructorDTO> InstructorPorRun(
        @Parameter(description = "RUN del instructor", required = true, example = "12345678-9")
        @PathVariable String run) {
        if (instructorService.findById(run) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(instructorService.ObtenerInstructorDto(run));
    }

    @Operation(summary = "Iniciar sesión de instructor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string"))),
        @ApiResponse(responseCode = "401", description = "Credenciales inválidas",
                    content = @Content)
    })
    @GetMapping("/InicioSesion/{correo}/{contrasena}")
    public ResponseEntity<String> IniciarSesion(
        @Parameter(description = "Correo del instructor", required = true)
        @PathVariable String correo,
        @Parameter(description = "Contraseña del instructor", required = true)
        @PathVariable String contrasena) {
        String mensaje = instructorService.IniciarSesion(correo, contrasena);
        return ResponseEntity.ok(mensaje);
    }

    @Operation(summary = "Recuperar contraseña de instructor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proceso de recuperación iniciado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string"))),
        @ApiResponse(responseCode = "404", description = "Instructor no encontrado",
                    content = @Content)
    })
    @GetMapping("/RecuperarContrasena/{run}/{correo}")
    public ResponseEntity<String> RecuperarContrasena(
        @Parameter(description = "RUN del instructor", required = true, example = "12345678-9")
        @PathVariable String run,
        @Parameter(description = "Correo del instructor", required = true)
        @PathVariable String correo) {
        String mensaje = instructorService.RecuperarContrasena(run, correo);
        return ResponseEntity.ok(mensaje);
    }

    @Operation(summary = "Registrar nuevo instructor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Instructor registrado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Instructor.class))),
        @ApiResponse(responseCode = "400", description = "Error: El correo o RUN ya existe en el sistema",
                    content = @Content)
    })
    @PostMapping("/registrar")
    public ResponseEntity<?> saveInstructor(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del nuevo instructor",
            required = true,
            content = @Content(
                schema = @Schema(implementation = Instructor.class),
                examples = @ExampleObject(value = """
                    {
                        "run": "12345678-9",
                        "pnombre": "Ana",
                        "snombre": "María",
                        "appaterno": "López",
                        "apmaterno": "García",
                        "correo": "ana.lopez@academia.com",
                        "contrasena": "contraseña123",
                        "estado": "ACTIVO",
                        "mencion": "Programación Java",
                        "porcAprobacion": 75
                    }
                    """)
            )
        )
        @RequestBody Instructor instructor) {
        Instructor instructorGuardado = instructorService.save(instructor);
        
        if (instructorGuardado == null) {
            return ResponseEntity.badRequest()
                    .body("Error: El correo o RUN ya existe en el sistema");
        }
        
        return ResponseEntity.ok(instructorGuardado);
    }

    @Operation(summary = "Actualizar instructor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Instructor actualizado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Instructor.class))),
        @ApiResponse(responseCode = "404", description = "Instructor no encontrado",
                    content = @Content)
    })
    @PutMapping("/actualizar/{run}")
    public ResponseEntity<?> updateInstructor(
        @Parameter(description = "RUN del instructor", required = true, example = "12345678-9")
        @PathVariable String run,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos actualizados del instructor",
            required = true,
            content = @Content(
                schema = @Schema(implementation = Instructor.class),
                examples = @ExampleObject(value = """
                    {
                        "pnombre": "Ana",
                        "snombre": "María",
                        "appaterno": "López",
                        "apmaterno": "García",
                        "correo": "ana.lopez@academia.com",
                        "estado": "ACTIVO",
                        "mencion": "Programación Java",
                        "porcAprobacion": 75
                    }
                    """)
        )
    )
    @RequestBody Instructor instructor) {
    return ResponseEntity.ok(instructorService.update(run, instructor));
}

    @Operation(summary = "Eliminar instructor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Instructor eliminado exitosamente",
                    content = @Content),
        @ApiResponse(responseCode = "404", description = "Instructor no encontrado",
                    content = @Content)
    })
    @DeleteMapping("/eliminar/{run}")
    public void deleteInstructor(
        @Parameter(description = "RUN del instructor", required = true, example = "12345678-9")
        @PathVariable String run) {
        instructorService.delete(instructorService.findById(run));
    }
}