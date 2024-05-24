package edu.carlosliam.trabajos.models.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="trabajador")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idTrabajador")
public class Trabajador implements Serializable{
	@JsonIgnore
	private static final String DNI_CHARS = "TRWAGMYFPDXBNJZSQVHLCKE";
	
	@JsonIgnore
	private static final String NIE_CHARS = "XYZ";
	
    @JsonIgnore
    private static final String DNI_REGEX = "^(?:[0-9]{8}[" + DNI_CHARS + "]|[XYZ][0-9]{7}[" + DNI_CHARS + "])$";
    
    @JsonIgnore
    private static final String ESPECIALIDAD_REGEX = "^(electricidad|limpieza|gestion|fontaneria|carpinteria|pintura|construccion)$";
	
	@Id
	@NotEmpty(message = "no puede estar vacío")
	@Size(max = 5, message = "debe tener un tamaño de hasta 5 caracteres")
	@Column(name="id_trabajador")
	private String idTrabajador;
	
	@NotEmpty(message = "no puede estar vacío")
	@Pattern(regexp = DNI_REGEX, message = "no tiene un formato válido")
	@Column(nullable = false, unique = true)
	private String dni;
	
	@NotEmpty(message = "no puede estar vacío")
	@Size(min = 2, max = 100, message = "debe tener un tamaño entre 2 y 100 caracteres")
	@Column(nullable = false)
	private String nombre;
	
	@NotEmpty(message = "no puede estar vacío")
	@Size(min = 2, max = 100, message = "debe tener un tamaño entre 2 y 100 caracteres")
	@Column(nullable = false)
	private String apellidos;
	
	@NotEmpty(message = "no puede estar vacío")
	@Pattern(regexp = ESPECIALIDAD_REGEX, message = "no tiene un valor válido")
	@Column(nullable = false)
	private String especialidad;
	
	@NotEmpty(message = "no puede estar vacío")
	@Size(min = 2, max = 100, message = "debe tener un tamaño entre 2 y 100 caracteres")
	@Column(nullable = false)
	private String contraseña;
	
	@NotEmpty(message = "no puede estar vacío")
	@Column(nullable = false, unique = true)
	@Email(message = "no es una dirección de correo bien formada")
	private String email;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "trabajador")
	private Set<Trabajo> trabajos = new HashSet<Trabajo>(0);
	
	private static final long serialVersionUID = 1L;
	
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

	/**
	 * Valida un DNI (Ya sea NIF o NIE)
	 * @param dni El DNI que se quiere validar
	 * @return Verdadero si es un DNI válido, falso si no lo es
	 */
	public static boolean validarDni(String dni) {
		return NIE_CHARS.indexOf(dni.charAt(0)) >= 0 ? validarNie(dni) : validarNif(dni);
	}
	
	private static boolean validarNif(String nif) {
		int parteNumerica = Integer.parseInt(nif.substring(0, 8));
		char digitoControl = nif.charAt(8);
		
		return DNI_CHARS.charAt(parteNumerica % 23) == digitoControl;
	}
	
	private static boolean validarNie(String nie) {
		return validarNif(nie.replace('X', '0').replace('Y', '1').replace('Z', '2'));
	}
}
