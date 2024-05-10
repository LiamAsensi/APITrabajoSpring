package edu.carlosliam.trabajos.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.carlosliam.trabajos.models.dao.ItrabajoDAO;
import edu.carlosliam.trabajos.models.entity.Trabajo;

@Service
public class TrabajoServiceImpl implements ITrabajoService{
	
	@Autowired
	private ItrabajoDAO trabajoDAO;
	
	@Override
	@Transactional(readOnly = true)
	public List<Trabajo> findAll() {
		return (List<Trabajo>) trabajoDAO.findAll();
	}
	
	@Override
	@Transactional()
	public Trabajo save(Trabajo trabajo) {
		return trabajoDAO.save(trabajo);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Trabajo findById(String id) {
		return trabajoDAO.findById(id).orElse(null);
	}
	
	@Override
	@Transactional()
	public void delete(Trabajo trabajo) {
		trabajoDAO.delete(trabajo);
	}
}
