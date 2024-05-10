package edu.carlosliam.trabajos.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

import edu.carlosliam.trabajos.models.entity.Trabajador;
import edu.carlosliam.trabajos.models.entity.Trabajo;
import edu.carlosliam.trabajos.models.services.ITrabajoService;
import jakarta.validation.Valid;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping(value = "/api", produces = "application/json; charset=UTF-8")
public class TrabajoRestController {
	
	@Autowired
	private ITrabajoService trabajoService;
	
	// Crear respuestas de error
	private ResponseEntity<?> createErrorResponse(HttpStatus status, String errorMessage) {
	    Map<String, Object> response = new HashMap<>();
	    
	    response.put("error", true);
	    response.put("errorMessage", errorMessage);
	    
	    return new ResponseEntity<>(response, status);
	}
	
	// Crear respuestas correctas
	private ResponseEntity<?> createResultResponse(HttpStatus status, Object result) {
		Map<String, Object> response = new HashMap<>();
		
		response.put("error", false);
		response.put("result", result);
		
		return new ResponseEntity<>(response, status);
	}
	
	/*@GetMapping("/trabajos")
	public List<Trabajo> index() {
		return trabajoService.findAll();
	}*/
	
	@GetMapping("/trabajos")
	public ResponseEntity<?> index() {
		List<Trabajo> trabajo = new ArrayList<>();
		
		try {
			trabajo.addAll(this.trabajoService.findAll());
		} catch(DataAccessException e) {
			return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, 
					"Error al realizar la consulta en la base de datos.");
		}
		
		return createResultResponse(HttpStatus.OK, trabajo);
	}
	
	@GetMapping("/trabajos/{id}")
	public Trabajo show(@PathVariable String id) {
		return trabajoService.findById(id);
	}
	
	@PostMapping("/trabajos")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@Valid @RequestBody Trabajo trabajo, BindingResult result) {
		Trabajo trabajoNuevo = null;
		
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			
			return createErrorResponse(HttpStatus.BAD_REQUEST, String.join(", ", errors));
		}
		
		try {
			// Comprobación de que el ID no se encuentra ya en la BBDD
			if (this.trabajoService.findById(trabajo.getCodTrab()) != null) {
				return createErrorResponse(HttpStatus.CONFLICT,
						"El trabajo con el ID: ".concat(trabajo.getCodTrab()).concat(" ya existe."));
			} 
			
			trabajoNuevo = this.trabajoService.save(trabajo);
		} catch (DataAccessException e) {
			return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error al realizar la inserción en la base de datos.");
		}
		
		return createResultResponse(HttpStatus.CREATED, trabajoNuevo);
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
