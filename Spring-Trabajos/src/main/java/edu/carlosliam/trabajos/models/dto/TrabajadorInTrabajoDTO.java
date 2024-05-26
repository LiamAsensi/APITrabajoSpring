package edu.carlosliam.trabajos.models.dto;

import edu.carlosliam.trabajos.models.entity.Trabajador;

public class TrabajadorInTrabajoDTO {
    private String idTrabajador;
    private String dni;
    private String nombre;
    private String apellidos;
    private String especialidad;
    private String contraseña;
    private String email;

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

    public static TrabajadorInTrabajoDTO convertToDTO(Trabajador trabajador) {
        TrabajadorInTrabajoDTO trabajadorDTO = new TrabajadorInTrabajoDTO();

        trabajadorDTO.setIdTrabajador(trabajador.getIdTrabajador());
        trabajadorDTO.setNombre(trabajador.getNombre());
        trabajadorDTO.setApellidos(trabajador.getApellidos());
        trabajadorDTO.setEspecialidad(trabajador.getEspecialidad());
        trabajadorDTO.setDni(trabajador.getDni());
        trabajadorDTO.setEmail(trabajador.getEmail());
        trabajadorDTO.setContraseña(trabajador.getContraseña());

        return trabajadorDTO;
    }

    public static Trabajador convertToEntity(TrabajadorInTrabajoDTO trabajador) {
        Trabajador trabajadorEntity = new Trabajador();

        trabajadorEntity.setIdTrabajador(trabajador.getIdTrabajador());
        trabajadorEntity.setNombre(trabajador.getNombre());
        trabajadorEntity.setApellidos(trabajador.getApellidos());
        trabajadorEntity.setEspecialidad(trabajador.getEspecialidad());
        trabajadorEntity.setDni(trabajador.getDni());
        trabajadorEntity.setEmail(trabajador.getEmail());
        trabajadorEntity.setContraseña(trabajador.getContraseña());

        return trabajadorEntity;
    }
}
