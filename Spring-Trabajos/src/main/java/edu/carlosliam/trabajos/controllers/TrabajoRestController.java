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
	
	/*
	 * Servicio para obtener un listado de todos los trabajos almacenados
	 */
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
	
	
	/*
	 * Servicio para obtener un trabajo por su ID pasado por parámetro
	 */
	@GetMapping("/trabajos/{id}")
	public ResponseEntity<?> show(@PathVariable String id) {
		Trabajo trabajo = null;
				
		try {
			trabajo = this.trabajoService.findById(id);
		} catch (DataAccessException e) {
			return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error al realizar la consulta en la base de datos.");
		}
		
		if (trabajo == null) {
			return createErrorResponse(HttpStatus.NOT_FOUND,
					"El trabajo con el ID: ".concat(id).concat(" no se ha encontrado."));
		}
		
		return createResultResponse(HttpStatus.OK, trabajo);
	}
	
	/*
	 * Servicio para insertar un nuevo trabajo en la base de datos.
	 */
	@PostMapping("/trabajos")
	//@ResponseStatus(HttpStatus.CREATED)
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
	public ResponseEntity<?> update(@Valid @RequestBody Trabajo trabajo, BindingResult result, @PathVariable String id) {
		Trabajo trabajoUpdate = null;
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			
			return createErrorResponse(HttpStatus.BAD_REQUEST, String.join(", ", errors));
		}
		
		try {
			Trabajo trabajoActual = this.trabajoService.findById(id);
			
			if (trabajoActual == null) {
				return createErrorResponse(HttpStatus.NOT_FOUND,
						"El trabajo con el ID: ".concat(id).concat(" no se ha encontrado."));
			}
			
			trabajoActual.setCategoria(trabajo.getCategoria());
			trabajoActual.setDescripcion(trabajo.getDescripcion());
			trabajoActual.setFecFin(trabajo.getFecFin());
			trabajoActual.setFecIni(trabajo.getFecIni());
			trabajoActual.setPrioridad(trabajo.getPrioridad());
			trabajoActual.setTiempo(trabajo.getTiempo());
			trabajoActual.setTrabajador(trabajo.getTrabajador());
			
			trabajoUpdate = this.trabajoService.save(trabajoActual);	
		} catch (DataAccessException e) {
			return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error al actualizar el trabajo en la base de datos.");
		}		
		
		return createResultResponse(HttpStatus.CREATED, trabajoUpdate);
	}
	
	@DeleteMapping("/trabajos/{id}")
	public ResponseEntity<?> delete(@PathVariable String id) {
		try {
			Trabajo currentTrabajo = this.trabajoService.findById(id);
			
			if (currentTrabajo == null) {
				return createErrorResponse(HttpStatus.NOT_FOUND,
						"El trabajo con el ID: ".concat(id).concat(" no se ha encontrado."));
			}
			
			this.trabajoService.delete(id);
		} catch (DataAccessException e) {
			return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error al eliminar el trabajo de la base de datos.");
		}
		
		return createResultResponse(HttpStatus.OK, "Trabajo eliminado con éxito.");
	}
}
