package com.edutech.gestionusuario.service;

import com.edutech.gestionusuario.model.DTO.EstudianteDTO;
import com.edutech.gestionusuario.model.Estudiante;
import com.edutech.gestionusuario.repository.EstudianteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Transactional

public class EstudianteService {
    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<Estudiante> findAll() {
        
        return estudianteRepository.findAll();
    }

    public Estudiante findById(String run) {
        
        return estudianteRepository.findById(run).get();
    }

    public Estudiante save(Estudiante estudiante) {
        List<Estudiante> estudiantes = estudianteRepository.findAll();
        for (Estudiante estudiante1 : estudiantes) {
            if (estudiante1.getCorreo().equals(estudiante.getCorreo())) {
                return null;
            }
            else if (estudiante1.getRun().equals(estudiante.getRun())) {
                return null;
            }
        }
        return estudianteRepository.save(estudiante);
    }

    public void delete(Estudiante estudiante) {
        
        estudianteRepository.delete(estudiante);
    }

    public Estudiante update(String id_estudiante, Estudiante estudiante) {
        Estudiante estudianteUpdate = estudianteRepository.findById(id_estudiante)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        estudianteUpdate.setPnombre(estudiante.getPnombre());
        estudianteUpdate.setSnombre(estudiante.getSnombre());
        estudianteUpdate.setAppaterno(estudiante.getAppaterno());
        estudianteUpdate.setApmaterno(estudiante.getApmaterno());
        estudianteUpdate.setCorreo(estudiante.getCorreo());
        estudianteUpdate.setCalificacion(estudiante.getCalificacion());
        estudianteUpdate.setPorcAsistencia(estudiante.getPorcAsistencia());
        estudianteUpdate.setEstado(estudiante.getEstado());
        return estudianteRepository.save(estudianteUpdate);
    }

    public String IniciarSesion(String correo, String contrasena){
        List<Estudiante> estudiantes = estudianteRepository.findAll();

        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getCorreo().equals(correo) && estudiante.getContrasena().equals(contrasena)) {
                return ("Inicio de sesión exitoso, bienvenido:" + estudiante.getPnombre() + estudiante.getAppaterno());
            }
        }
        return ("Correo o contraseña incorrectos, intente nuevamente.");
    }
    
    public String RecuperarContrasena(String run,String correo){
        Estudiante estudiante = estudianteRepository.findById(run).get();
        if(estudiante.getCorreo().equals(correo)){
            return ("Se ha enviado un correo para recuperar la contraseña.");
        }else{
            return ("El correo ingresado no corresponde al usuario.");
        }
    }


    public String ObtenerCursosEstudiante(Integer id_curso){
        try {
            String CursoUrl = "http://localhost:8081/api/v1/cursos/obtener/" + id_curso;
            String cursoData = restTemplate.getForObject(CursoUrl, String.class);
            List<Estudiante> estudiantes = estudianteRepository.findAllByidCurso(id_curso);

            if(cursoData == null){
                return "El curso no existe.";
            } else {
                StringBuilder resultado = new StringBuilder();
                resultado.append("=== INFORMACIÓN DEL CURSO ===\n");
                resultado.append(cursoData).append("\n\n");
                resultado.append("=== ESTUDIANTES INSCRITOS ===\n");

                if(estudiantes.isEmpty()) {
                    resultado.append("\nNo hay estudiantes inscritos en este curso.");
                } else {
                    for (Estudiante e : estudiantes) {
                        resultado.append("\n- Nombre: ").append(e.getPnombre()).append(" ").append(e.getAppaterno())
                                .append("\n  RUN: ").append(e.getRun())
                                .append("\n  Correo: ").append(e.getCorreo())
                                .append("\n  Calificación: ").append(e.getCalificacion())
                                .append("\n  Promedio: ").append(e.getPromCalificaciones())
                                .append("\n  Asistencia: ").append(e.getPorcAsistencia()).append("%\n");
                    }
                }

                return resultado.toString();
            }
        } catch (Exception e) {
            return "Error al obtener la información del curso, el curso " + id_curso + " no existe";
        }
    }

    public EstudianteDTO obtenerEstudianteDTO(String run){

        Estudiante estudiante = estudianteRepository.findById(run).get();
        EstudianteDTO nuevoestudianteDTO = new EstudianteDTO(
                estudiante.getRun(),
                estudiante.getPnombre(),
                estudiante.getAppaterno(),
                estudiante.getCorreo(),
                estudiante.getIdCurso().toString()
        );
        return nuevoestudianteDTO;
    }
}