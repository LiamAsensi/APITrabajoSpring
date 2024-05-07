package edu.carlosliam.trabajos.models.services;

import java.util.List;

import edu.carlosliam.trabajos.models.entity.Trabajador;

public interface ITrabajadorService {

	public List<Trabajador> findAll();
	
	public Trabajador findById(String id);
	
	public Trabajador save(Trabajador trabajador);
	
	public void delete(String id);
}
