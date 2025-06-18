package com.edutech.gestionusuario;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.edutech.gestionusuario.model.Gerente;
import com.edutech.gestionusuario.repository.GerenteRepository;
import com.edutech.gestionusuario.service.GerenteService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;


class GerenteServiceTest {

    @Mock
    private GerenteRepository gerenteRepository;

    @InjectMocks
    private GerenteService gerenteService;

    private Gerente gerente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        gerente = new Gerente();
        gerente.setRun("12.345.678-9");
        gerente.setPnombre("Juan");
        gerente.setSnombre("Carlos");
        gerente.setAppaterno("Perez");
        gerente.setApmaterno("Lopez");
        gerente.setCorreo("juan@example.com");
        gerente.setDepartamento("Ventas");
        gerente.setEstado("activo");
        gerente.setContrasena("1234");
    }

    @Test
    void testFindAll() {
        List<Gerente> lista = Arrays.asList(gerente);
        when(gerenteRepository.findAll()).thenReturn(lista);

        List<Gerente> result = gerenteService.findAll();

        assertEquals(1, result.size());
        verify(gerenteRepository).findAll();
    }

    @Test
    void testFindById() {
        when(gerenteRepository.findById("12.345.678-9")).thenReturn(Optional.of(gerente));

        Gerente result = gerenteService.findById("12.345.678-9");

        assertEquals("Juan", result.getPnombre());
        verify(gerenteRepository).findById("12.345.678-9");
    }

    @Test
    void testSave() {
        when(gerenteRepository.findAll()).thenReturn(Collections.emptyList());
        when(gerenteRepository.save(any(Gerente.class))).thenReturn(gerente);

        Gerente nuevo = new Gerente();
        nuevo.setRun("98.765.432-1");
        nuevo.setCorreo("nuevo@example.com");

        Gerente saved = gerenteService.save(nuevo);

        assertNotNull(saved);
        verify(gerenteRepository).save(nuevo);
    }



    @Test
    void testDelete() {
        doNothing().when(gerenteRepository).delete(gerente);

        gerenteService.delete(gerente);

        verify(gerenteRepository).delete(gerente);
    }

    @Test
    void testUpdate() {
        when(gerenteRepository.findById("12.345.678-9")).thenReturn(Optional.of(gerente));
        when(gerenteRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Gerente actualizado = new Gerente();
        actualizado.setPnombre("Pedro");
        actualizado.setSnombre("Alonso");
        actualizado.setAppaterno("Gomez");
        actualizado.setApmaterno("Sanchez");
        actualizado.setCorreo("pedro@example.com");
        actualizado.setDepartamento("Marketing");
        actualizado.setEstado("activo");

        Gerente result = gerenteService.update("12.345.678-9", actualizado);

        assertEquals("Pedro", result.getPnombre());
        assertEquals("Gomez", result.getAppaterno());
        assertEquals("Marketing", result.getDepartamento());
    }

    @Test
    void testIniciarSesion() {
        when(gerenteRepository.findAll()).thenReturn(Arrays.asList(gerente));

        String mensaje = gerenteService.IniciarSesion("juan@example.com", "1234");

        assertTrue(mensaje.contains("Inicio de sesión exitoso"));
        assertTrue(mensaje.contains("Juan"));
    }



    @Test
    void testRecuperarContrasena() {
        when(gerenteRepository.findById("12.345.678-9")).thenReturn(Optional.of(gerente));

        String mensaje = gerenteService.RecuperarContrasena("12.345.678-9", "juan@example.com");

        assertEquals("Se ha enviado un correo para recuperar la contraseña.", mensaje);
    }


}

