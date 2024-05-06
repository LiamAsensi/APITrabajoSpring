package edu.carlosliam.trabajos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.carlosliam.trabajos.models.entity.Trabajador;
import edu.carlosliam.trabajos.models.services.ITrabajadorService;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api")
public class TrabajadorRestController {
	
	@Autowired
	private ITrabajadorService trabajadorService;
	
	@GetMapping("/trabajadores")
	public List<Trabajador> index() {
		return trabajadorService.findAll();
	}
}
