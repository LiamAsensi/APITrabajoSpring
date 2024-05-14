package edu.carlosliam.trabajos.models.dto;

import edu.carlosliam.trabajos.models.entity.Trabajador;

import java.util.Set;
import java.util.stream.Collectors;

public class TrabajadorDTO {
    private String idTrabajador;
    private String dni;
    private String nombre;
    private String apellidos;
    private String especialidad;
    private String contraseña;
    private String email;
    private Set<TrabajoInTrabajadorDTO> trabajos;

    public String getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(String idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<TrabajoInTrabajadorDTO> getTrabajos() {
        return trabajos;
    }

    public void setTrabajos(Set<TrabajoInTrabajadorDTO> trabajos) {
        this.trabajos = trabajos;
    }

    public static TrabajadorDTO convertToDTO(Trabajador trabajador) {
        TrabajadorDTO trabajadorDTO = new TrabajadorDTO();

        trabajadorDTO.setIdTrabajador(trabajador.getIdTrabajador());
        trabajadorDTO.setNombre(trabajador.getNombre());
        trabajadorDTO.setApellidos(trabajador.getApellidos());
        trabajadorDTO.setEspecialidad(trabajador.getEspecialidad());
        trabajadorDTO.setDni(trabajador.getDni());
        trabajadorDTO.setEmail(trabajador.getEmail());
        trabajadorDTO.setContraseña(trabajador.getContraseña());
        trabajadorDTO.setTrabajos(trabajador
                .getTrabajos()
                .stream()
                .map(TrabajoInTrabajadorDTO::convertToDTO)
                .collect(Collectors.toSet()
                )
        );

        return trabajadorDTO;
    }
}
