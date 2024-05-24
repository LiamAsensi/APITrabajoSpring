package edu.carlosliam.trabajos.models.entity;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.*;

@Entity
@Table(name="trabajo")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "codTrab")
public class Trabajo implements Serializable{
	@JsonIgnore
	private static final String ESPECIALIDAD_REGEX = "^(electricidad|limpieza|gestion|fontaneria|carpinteria|pintura|construccion)$";

	@Id
	@Size(min = 1, max = 5, message = "debe tener entre 1 y 5 caracteres.")
	@Column(name = "cod_trabajo")
	private String codTrab;
	
	@NotEmpty(message = "no puede estar vacio.")
	@Pattern(regexp = ESPECIALIDAD_REGEX, message = "no tiene un formato válido")
	@Column(nullable = false)
	private String categoria;

	@NotEmpty(message = "no puede estar vacio.")
	@Size(min = 1, max = 500, message = "debe tener entre 1 y 500 caracteres.")
	@Column(nullable = false)
	private String descripcion;

	@NotNull(message = "no puede estar vacio.")
	@Column(name = "fec_ini", nullable=false)
	@Temporal(TemporalType.DATE)
	private LocalDate fecIni;
	
	@Column(name = "fec_fin")
	@Temporal(TemporalType.DATE)
	private LocalDate fecFin;

	@DecimalMax(value = "999.9", message = "no puede superar las 999.9h.")
	private Float tiempo;
	
	@NotNull(message = "no puede estar vacio.")
	@Max(value = 9, message = "no puede superar 9.")
	@Column(nullable = false)
	private int prioridad;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_trabajador")
	private Trabajador trabajador;
	
	public String getCodTrab() {
		return codTrab;
	}

	public void setCodTrab(String codTrab) {
		this.codTrab = codTrab;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public LocalDate getFecIni() {
		return fecIni;
	}

	public void setFecIni(LocalDate fecIni) {
		this.fecIni = fecIni;
	}

	public LocalDate getFecFin() {
		return fecFin;
	}

	public void setFecFin(LocalDate fecFin) {
		this.fecFin = fecFin;
	}

	public Float getTiempo() {
		return tiempo;
	}

	public void setTiempo(Float tiempo) {
		this.tiempo = tiempo;
	}

	public int getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(int prioridad) {
		this.prioridad = prioridad;
	}

	public Trabajador getTrabajador() {
		return trabajador;
	}

	public void setTrabajador(Trabajador trabajador) {
		this.trabajador = trabajador;
	}

	private static final long serialVersionUID = 1L;
}
