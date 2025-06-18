package com.edutech.gestionusuario.controller;

import com.edutech.gestionusuario.model.Gerente;
import com.edutech.gestionusuario.service.GerenteService;
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
import io.swagger.v3.oas.annotations.media.ExampleObject;

import java.util.List;

@RestController
@RequestMapping("/api/v1/gerentes")
@Tag(name = "Gerentes", description = "API de gestión de gerentes")
public class GerenteController {

    @Autowired
    private GerenteService gerenteService;

    @Operation(summary = "Listar todos los gerentes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de gerentes encontrada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "array", implementation = Gerente.class))),
        @ApiResponse(responseCode = "404", description = "No hay gerentes registrados",
                    content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<Gerente>> ListarGerentes() {
        List<Gerente> gerentes = gerenteService.findAll();
        if (gerentes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(gerentes);
    }

    @Operation(summary = "Obtener gerente por RUN")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Gerente encontrado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Gerente.class))),
        @ApiResponse(responseCode = "404", description = "Gerente no encontrado",
                    content = @Content)
    })
    @GetMapping("/{run}")
    public ResponseEntity<Gerente> GerentePorRun(
        @Parameter(description = "RUN del gerente", required = true, example = "12345678-9")
        @PathVariable String run) {
        Gerente gerente = gerenteService.findById(run);
        if (gerente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(gerente);
    }

    @Operation(summary = "Iniciar sesión de gerente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string"))),
        @ApiResponse(responseCode = "401", description = "Credenciales inválidas",
                    content = @Content)
    })
    @GetMapping("/InicioSesion/{correo}/{contrasena}")
    public ResponseEntity<String> IniciarSesion(
        @Parameter(description = "Correo del gerente", required = true)
        @PathVariable String correo,
        @Parameter(description = "Contraseña del gerente", required = true)
        @PathVariable String contrasena) {
        String mensaje = gerenteService.IniciarSesion(correo, contrasena);
        return ResponseEntity.ok(mensaje);
    }

    @Operation(summary = "Recuperar contraseña de gerente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proceso de recuperación iniciado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string"))),
        @ApiResponse(responseCode = "404", description = "Gerente no encontrado",
                    content = @Content)
    })
    @GetMapping("/RecuperarContrasena/{run}/{correo}")
    public ResponseEntity<String> RecuperarContrasena(
        @Parameter(description = "RUN del gerente", required = true, example = "12345678-9")
        @PathVariable String run,
        @Parameter(description = "Correo del gerente", required = true)
        @PathVariable String correo) {
        String mensaje = gerenteService.RecuperarContrasena(run, correo);
        return ResponseEntity.ok(mensaje);
    }

    @Operation(summary = "Registrar nuevo gerente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Gerente registrado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Gerente.class))),
        @ApiResponse(responseCode = "400", description = "Error: El correo o RUN ya existe en el sistema",
                    content = @Content)
    })
    @PostMapping("/registrar")
    public ResponseEntity<?> saveGerente(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del nuevo gerente",
            required = true,
            content = @Content(
                schema = @Schema(implementation = Gerente.class),
                examples = @ExampleObject(value = """
                    {
                        "run": "12345678-9",
                        "pnombre": "Carlos",
                        "snombre": "Alberto",
                        "appaterno": "Rodríguez",
                        "apmaterno": "Sánchez",
                        "correo": "carlos.rodriguez@empresa.com",
                        "contrasena": "contraseña123",
                        "estado": "ACTIVO",
                        "departamento": "Recursos Humanos"
                    }
                    """)
            )
        )
        @RequestBody Gerente gerente) {
        Gerente gerenteGuardado = gerenteService.save(gerente);

        if (gerenteGuardado == null) {
            return ResponseEntity.badRequest()
                    .body("Error: El correo o RUN ya existe en el sistema");
        }

        return ResponseEntity.ok(gerenteGuardado);
    }

    @Operation(summary = "Actualizar gerente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Gerente actualizado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Gerente.class))),
        @ApiResponse(responseCode = "404", description = "Gerente no encontrado",
                    content = @Content)
    })
    @PutMapping("/actualizar/{run}")
    public ResponseEntity<?> updateGerente(
        @Parameter(description = "RUN del gerente", required = true, example = "12345678-9")
        @PathVariable String run,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos actualizados del gerente",
            required = true,
            content = @Content(
                schema = @Schema(implementation = Gerente.class),
                examples = @ExampleObject(value = """
                    {
                        "pnombre": "Carlos",
                        "snombre": "Alberto",
                        "appaterno": "Rodríguez",
                        "apmaterno": "Sánchez",
                        "correo": "carlos.rodriguez@empresa.com",
                        "estado": "ACTIVO",
                        "departamento": "Recursos Humanos"
                    }
                    """)
        )
    )
    @RequestBody Gerente gerente) {
    return ResponseEntity.ok(gerenteService.update(run, gerente));
}

    @Operation(summary = "Eliminar gerente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Gerente eliminado exitosamente",
                    content = @Content),
        @ApiResponse(responseCode = "404", description = "Gerente no encontrado",
                    content = @Content)
    })
    @DeleteMapping("/eliminar/{run}")
    public void deleteGerente(
        @Parameter(description = "RUN del gerente", required = true, example = "12345678-9")
        @PathVariable String run) {
        gerenteService.delete(gerenteService.findById(run));
    }
}