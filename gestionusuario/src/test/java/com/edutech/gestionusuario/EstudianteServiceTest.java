package com.edutech.gestionusuario;

import com.edutech.gestionusuario.model.DTO.EstudianteDTO;
import com.edutech.gestionusuario.model.Estudiante;
import com.edutech.gestionusuario.repository.EstudianteRepository;
import com.edutech.gestionusuario.service.EstudianteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class EstudianteServiceTest {

    @Mock
    private EstudianteRepository estudianteRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private EstudianteService estudianteService;

    private Estudiante estudiante;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        estudiante = new Estudiante();
        estudiante.setRun("12.345.678-9");
        estudiante.setPnombre("Juan");
        estudiante.setAppaterno("Perez");
        estudiante.setCorreo("juan@example.com");
        estudiante.setContrasena("1234");
        estudiante.setIdCurso(1);
    }

    @Test
    void testFindAll() {
        when(estudianteRepository.findAll()).thenReturn(List.of(estudiante));

        List<Estudiante> result = estudianteService.findAll();

        assertEquals(1, result.size());
        verify(estudianteRepository).findAll();
    }
    
    @Test
    void testFindById() {
        when(estudianteRepository.findById("12.345.678-9")).thenReturn(Optional.of(estudiante));

        Estudiante result = estudianteService.findById("12.345.678-9");

        assertNotNull(result);
        assertEquals("Juan", result.getPnombre());
    }

    @Test
    void testSave() {
        when(estudianteRepository.findAll()).thenReturn(List.of());
        when(estudianteRepository.save(estudiante)).thenReturn(estudiante);

        Estudiante result = estudianteService.save(estudiante);

        assertNotNull(result);
        verify(estudianteRepository).save(estudiante);
    }


    @Test
    void testDelete() {
        doNothing().when(estudianteRepository).delete(estudiante);

        estudianteService.delete(estudiante);

        verify(estudianteRepository).delete(estudiante);
    }

    @Test
    void testUpdate() {
        when(estudianteRepository.findById("12.345.678-9")).thenReturn(Optional.of(estudiante));
        when(estudianteRepository.save(any())).thenReturn(estudiante);

        Estudiante nuevo = new Estudiante();
        nuevo.setPnombre("Pedro");
        nuevo.setAppaterno("Gomez");
        nuevo.setCorreo("pedro@example.com");
        nuevo.setCalificacion(6.0f);
        nuevo.setPorcAsistencia(90);
        nuevo.setEstado("aprobado");

        Estudiante result = estudianteService.update("12.345.678-9", nuevo);

        assertEquals("Pedro", result.getPnombre());
        assertEquals("Gomez", result.getAppaterno());
    }

    @Test
    void testIniciarSesion() {
        when(estudianteRepository.findAll()).thenReturn(List.of(estudiante));

        String result = estudianteService.IniciarSesion("juan@example.com", "1234");

        assertTrue(result.contains("Inicio de sesión exitoso"));
    }


    @Test
    void testRecuperarContrasena() {
        when(estudianteRepository.findById("12.345.678-9")).thenReturn(Optional.of(estudiante));

        String result = estudianteService.RecuperarContrasena("12.345.678-9", "juan@example.com");

        assertTrue(result.contains("Se ha enviado un correo"));
    }


    @Test
    void testObtenerEstudianteDTO() {
        when(estudianteRepository.findById("12.345.678-9")).thenReturn(Optional.of(estudiante));

        EstudianteDTO dto = estudianteService.obtenerEstudianteDTO("12.345.678-9");

        assertEquals("Juan", dto.getPnombre());
        assertEquals("1", dto.getIdcurso());
    }
}
