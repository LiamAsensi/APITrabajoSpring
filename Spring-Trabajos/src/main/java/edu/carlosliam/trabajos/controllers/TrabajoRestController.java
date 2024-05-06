package edu.carlosliam.trabajos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.carlosliam.trabajos.models.entity.Trabajo;
import edu.carlosliam.trabajos.models.services.ITrabajoService;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api")
public class TrabajoRestController {
	
	@Autowired
	private ITrabajoService trabajoService;
	
	@GetMapping("/trabajos")
	public List<Trabajo> index() {
		return trabajoService.findAll();
	}
}
