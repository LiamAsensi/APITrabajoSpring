package edu.carlosliam.trabajos.models.services;

import java.util.List;

import edu.carlosliam.trabajos.models.entity.Trabajo;

public interface ITrabajoService {

	public List<Trabajo> findAll();
	
	public Trabajo save(Trabajo trabajo);

	public Trabajo findById(String id);
	
	public void delete(Trabajo trabajo);
}
