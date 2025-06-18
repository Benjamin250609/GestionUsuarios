package com.edutech.gestionusuario.service;

import com.edutech.gestionusuario.model.DTO.InstructorDTO;
import com.edutech.gestionusuario.model.Estudiante;
import com.edutech.gestionusuario.model.Instructor;
import com.edutech.gestionusuario.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorService {
    
    @Autowired
    private InstructorRepository instructorRepository;

    public List<Instructor> findAll() {
        return instructorRepository.findAll();
    }

    public Instructor findById(String run) {
        return instructorRepository.findById(run).get();
    }

    public Instructor save(Instructor instructor) {

        for (Instructor existente : instructorRepository.findAll()) {
            if (existente.getCorreo().equals(instructor.getCorreo())) {
                System.out.println("Correo duplicado: " + instructor.getCorreo());
                return null;
            }
            if (existente.getRun().equals(instructor.getRun())) {
                System.out.println("RUN duplicado: " + instructor.getRun());
                return null;
            }
        }

        return instructorRepository.save(instructor);
    }

    public void delete(Instructor instructor) {
        instructorRepository.delete(instructor);
    }

    public Instructor update(String id_instructor, Instructor instructor) {
        Instructor instructorUpdate = instructorRepository.findById(id_instructor)
                .orElseThrow(() -> new RuntimeException("Instructor no encontrado"));

        instructorUpdate.setPnombre(instructor.getPnombre());
        instructorUpdate.setSnombre(instructor.getSnombre());
        instructorUpdate.setAppaterno(instructor.getAppaterno());
        instructorUpdate.setApmaterno(instructor.getApmaterno());
        instructorUpdate.setCorreo(instructor.getCorreo());
        instructorUpdate.setMencion(instructor.getMencion());
        instructorUpdate.setPorcAprobacion(instructor.getPorcAprobacion());
        instructorUpdate.setEstado(instructor.getEstado());

        return instructorRepository.save(instructorUpdate);
    }

    public String IniciarSesion(String correo, String contrasena){
        List<Instructor> instructores = instructorRepository.findAll();

        for (Instructor instructor : instructores) {
            if (instructor.getCorreo().equals(correo) && instructor.getContrasena().equals(contrasena)) {
                return ("Inicio de sesión exitoso, bienvenido:" + instructor.getPnombre() + instructor.getAppaterno());
            }
        }
        return ("Correo o contraseña incorrectos, intente nuevamente.");
    }

    public String RecuperarContrasena(String run,String correo){
        Instructor instructor = instructorRepository.findById(run).get();
        if(instructor.getCorreo().equals(correo)){
            return ("Se ha enviado un correo para recuperar la contraseña.");
        }else{
            return ("El correo ingresado no corresponde al usuario.");
        }
    }

    public InstructorDTO ObtenerInstructorDto(String run) {

        Instructor instructor = instructorRepository.findById(run).get();
        InstructorDTO nuevoinstructorDto = new InstructorDTO(
                instructor.getRun(),
                instructor.getPnombre(),
                instructor.getAppaterno(),
                instructor.getCorreo(),
                instructor.getMencion()
        );
        return nuevoinstructorDto;
    }
}