package edu.carlosliam.trabajos.models.dao;

import org.springframework.data.repository.CrudRepository;

import edu.carlosliam.trabajos.models.entity.Trabajo;

public interface ItrabajoDAO extends CrudRepository<Trabajo, String> {

}
