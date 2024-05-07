package edu.carlosliam.trabajos.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
	
	@GetMapping("/trabajos/{id}")
	public Trabajo show(@PathVariable String id) {
		return trabajoService.findById(id);
	}
	
	@PostMapping("/trabajos")
	@ResponseStatus(HttpStatus.CREATED)
	public Trabajo create(@RequestBody Trabajo trabajo) {
		this.trabajoService.save(trabajo);
		return trabajo;
	}
	
	@PutMapping("/trabajos/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Trabajo update(@RequestBody Trabajo trabajo, @PathVariable String id) {
		Trabajo currentTrabajo = this.trabajoService.findById(id);
		currentTrabajo.setCategoria(trabajo.getCategoria());
		currentTrabajo.setDescripcion(trabajo.getDescripcion());
		currentTrabajo.setFecFin(trabajo.getFecFin());
		currentTrabajo.setFecIni(trabajo.getFecIni());
		currentTrabajo.setPrioridad(trabajo.getPrioridad());
		currentTrabajo.setTiempo(trabajo.getTiempo());
		currentTrabajo.setTrabajador(trabajo.getTrabajador());
		this.trabajoService.save(currentTrabajo);
		return currentTrabajo;
	}
	
	@DeleteMapping("/trabajos/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable String id) {
		Trabajo currentTrabajo = this.trabajoService.findById(id);
		this.trabajoService.delete(currentTrabajo);
	}
	
}
