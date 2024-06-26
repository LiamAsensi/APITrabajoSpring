package edu.carlosliam.trabajos.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.carlosliam.trabajos.models.dao.ItrabajadorDAO;
import edu.carlosliam.trabajos.models.entity.Trabajador;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TrabajadorServiceImpl implements ITrabajadorService{
	
	@Autowired
	private ItrabajadorDAO trabajadorDAO;
	
	@Override
	@Transactional(readOnly = true)
	public List<Trabajador> findAll() {
		return (List<Trabajador>) trabajadorDAO.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Trabajador findById(String id) {
		return trabajadorDAO.findById(id).orElse(null);
	}
	
	@Override
	@Transactional(readOnly = true) 
	public Trabajador findByDni(String dni) {
		return trabajadorDAO.findByDni(dni);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Trabajador findByEmail(String email) {
		return trabajadorDAO.findByEmail(email);
	}
	
	@Override
	@Transactional
	public Trabajador save(Trabajador trabajador) {
		return trabajadorDAO.save(trabajador);
	}
	
	@Override
	@Transactional
	public void delete(String id) {
		trabajadorDAO.deleteById(id);
	}
}
