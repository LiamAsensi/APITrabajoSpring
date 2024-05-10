package edu.carlosliam.trabajos.models.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="trabajador")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idTrabajador")
public class Trabajador implements Serializable{
	@Id
	@NotEmpty(message = "no puede estar vacío")
	@Size(max = 5, message = "el tamaño solo puede ser hasta 5 caracteres")
	@Column(name="id_trabajador")
	private String idTrabajador;
	
	@NotEmpty(message = "no puede estar vacío")
	@Size(min = 8, max = 9, message = "el tamaño debe ser entre 8 y 9 caracteres")
	@Column(nullable = false, unique = true)
	private String dni;
	
	@NotEmpty(message = "no puede estar vacío")
	@Size(min = 2, max = 100, message = "el tamaño debe ser entre 2 y 100 caracteres")
	@Column(nullable = false)
	private String nombre;
	
	@NotEmpty(message = "no puede estar vacío")
	@Size(min = 2, max = 100, message = "el tamaño debe ser entre 2 y 100 caracteres")
	@Column(nullable = false)
	private String apellidos;
	
	@NotEmpty(message = "no puede estar vacío")
	@Size(min = 2, max = 50, message = "el tamaño debe ser entre 2 y 50 caracteres")
	@Column(nullable = false)
	private String especialidad;
	
	@NotEmpty(message = "no puede estar vacío")
	@Size(min = 2, max = 50, message = "el tamaño debe ser entre 2 y 50 caracteres")
	@Column(nullable = false)
	private String contraseña;
	
	@NotEmpty(message = "no puede estar vacío")
	@Size(min = 2, max = 150, message = "el tamaño debe ser entre 2 y 150 caracteres")
	@Column(nullable = false, unique = true)
	@Email(message = "no es una dirección de correo bien formada")
	private String email;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "trabajador")
	private Set<Trabajo> trabajos = new HashSet<Trabajo>(0);
	
	public String getIdTrabajador() {
		return idTrabajador;
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
	
	public Set<Trabajo> getTrabajos() {
		return trabajos;
	}

	public void setTrabajos(Set<Trabajo> trabajos) {
		this.trabajos = trabajos;
	}

	private static final long serialVersionUID = 1L;
}
