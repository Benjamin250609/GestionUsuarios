package com.edutech.gestionusuario;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.edutech.gestionusuario.model.DTO.InstructorDTO;
import com.edutech.gestionusuario.model.Instructor;
import com.edutech.gestionusuario.repository.InstructorRepository;
import com.edutech.gestionusuario.service.InstructorService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;


class InstructorServiceTest {

    @Mock
    private InstructorRepository instructorRepository;

    @InjectMocks
    private InstructorService instructorService;

    private Instructor instructor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        instructor = new Instructor();
        instructor.setRun("12.345.678-9");
        instructor.setPnombre("Ana");
        instructor.setSnombre("Maria");
        instructor.setAppaterno("Lopez");
        instructor.setApmaterno("Gutierrez");
        instructor.setCorreo("ana@example.com");
        instructor.setMencion("Matemáticas");
        instructor.setPorcAprobacion(85);
        instructor.setEstado("activo");
        instructor.setContrasena("password123");
    }

    @Test
    void testFindAll() {
        List<Instructor> lista = Arrays.asList(instructor);
        when(instructorRepository.findAll()).thenReturn(lista);

        List<Instructor> result = instructorService.findAll();

        assertEquals(1, result.size());
        verify(instructorRepository).findAll();
    }

    @Test
    void testFindById() {
        when(instructorRepository.findById("12.345.678-9")).thenReturn(Optional.of(instructor));

        Instructor result = instructorService.findById("12.345.678-9");

        assertEquals("Ana", result.getPnombre());
        verify(instructorRepository).findById("12.345.678-9");
    }

    @Test
    void testSave() {
        when(instructorRepository.findAll()).thenReturn(Collections.emptyList());
        when(instructorRepository.save(any(Instructor.class))).thenReturn(instructor);

        Instructor nuevo = new Instructor();
        nuevo.setRun("98.765.432-1");
        nuevo.setCorreo("nuevo@example.com");

        Instructor saved = instructorService.save(nuevo);

        assertNotNull(saved);
        verify(instructorRepository).save(nuevo);
    }




    @Test
    void testDelete() {
        doNothing().when(instructorRepository).delete(instructor);

        instructorService.delete(instructor);

        verify(instructorRepository).delete(instructor);
    }

    @Test
    void testUpdate() {
        when(instructorRepository.findById("12.345.678-9")).thenReturn(Optional.of(instructor));
        when(instructorRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Instructor actualizado = new Instructor();
        actualizado.setPnombre("Luis");
        actualizado.setSnombre("Fernando");
        actualizado.setAppaterno("Martinez");
        actualizado.setApmaterno("Diaz");
        actualizado.setCorreo("luis@example.com");
        actualizado.setMencion("Física");
        actualizado.setPorcAprobacion(90);
        actualizado.setEstado("activo");

        Instructor result = instructorService.update("12.345.678-9", actualizado);

        assertEquals("Luis", result.getPnombre());
        assertEquals("Martinez", result.getAppaterno());
        assertEquals("Física", result.getMencion());
    }

    @Test
    void testIniciarSesion() {
        when(instructorRepository.findAll()).thenReturn(Arrays.asList(instructor));

        String mensaje = instructorService.IniciarSesion("ana@example.com", "password123");

        assertTrue(mensaje.contains("Inicio de sesión exitoso"));
        assertTrue(mensaje.contains("Ana"));
    }



    @Test
    void testRecuperarContrasena() {
        when(instructorRepository.findById("12.345.678-9")).thenReturn(Optional.of(instructor));

        String mensaje = instructorService.RecuperarContrasena("12.345.678-9", "ana@example.com");

        assertEquals("Se ha enviado un correo para recuperar la contraseña.", mensaje);
    }



    @Test
    void testObtenerInstructorDto() {
        when(instructorRepository.findById("12.345.678-9")).thenReturn(Optional.of(instructor));

        InstructorDTO dto = instructorService.ObtenerInstructorDto("12.345.678-9");

        assertEquals("12.345.678-9", dto.getRun());
        assertEquals("Ana", dto.getPnombre());
        assertEquals("Lopez", dto.getAppaterno());
        assertEquals("ana@example.com", dto.getCorreo());
        assertEquals("Matemáticas", dto.getMencion());
    }
}
