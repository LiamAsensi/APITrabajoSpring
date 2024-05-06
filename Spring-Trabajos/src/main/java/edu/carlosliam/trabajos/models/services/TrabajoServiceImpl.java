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
}
