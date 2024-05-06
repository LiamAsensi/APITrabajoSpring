package edu.carlosliam.trabajos.models.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="trabajador")
public class Trabajador implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="id_trabajador")
	private String idTrabajador;
	
	private String dni;
	private String nombre;
	private String apellidos;
	private String especialidad;
	private String contraseña;
	private String email;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "trabajador")
	private Set<Trabajo> trabajoses = new HashSet<Trabajo>(0);
	
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
	
	private static final long serialVersionUID = 1L;
}
