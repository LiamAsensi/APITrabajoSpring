package edu.carlosliam.trabajos.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
import edu.carlosliam.trabajos.models.services.ITrabajadorService;
import jakarta.validation.Valid;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping(value = "/api", produces = "application/json; charset=UTF-8")
public class TrabajadorRestController {
	
	@Autowired
	private ITrabajadorService trabajadorService;
	
	// Método para crear respuestas de error
	private ResponseEntity<?> createErrorResponse(HttpStatus status, String errorMessage) {
	    Map<String, Object> response = new HashMap<>();
	    
	    response.put("error", true);
	    response.put("errorMessage", errorMessage);
	    
	    return new ResponseEntity<>(response, status);
	}
	
	// Método para crear respuestas con un resultado correcto
	private ResponseEntity<?> createResultResponse(HttpStatus status, Object result) {
		Map<String, Object> response = new HashMap<>();
		
		response.put("error", false);
		response.put("result", result);
		
		return new ResponseEntity<>(response, status);
	}
	
	/**
	 * Servicio que devuelve una lista con el detalle de todos los trabajadores
	 * @return Una lista con los trabajadores
	 */
	@GetMapping("/trabajadores")
	public ResponseEntity<?> index() {
		List<Trabajador> trabajadores = new ArrayList<>();
		
		try {
			trabajadores.addAll(this.trabajadorService.findAll());
		} catch(DataAccessException e) {
			return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, 
					"Error al realizar la consulta en la base de datos.");
		}
		
		return createResultResponse(HttpStatus.OK, trabajadores);
	}
	
	/**
	 * Servicio para conseguir la información de un trabajador según su ID
	 * @param id El id del trabajador
	 * @return La información del trabajador si se encuentra
	 */
	@GetMapping("/trabajadores/{id}")
	public ResponseEntity<?> show(@PathVariable String id) {
		Trabajador trabajador = null;
		
		try {
			trabajador = this.trabajadorService.findById(id);
		} catch (DataAccessException e) {
			return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error al realizar la consulta en la base de datos.");
		}
		
		// Comprobación de que existe un trabajador con el ID de los parámetros
		if (trabajador == null) {
			return createErrorResponse(HttpStatus.NOT_FOUND,
					"El trabajador con el ID: ".concat(id).concat(" no se ha encontrado."));
		}
		
		return createResultResponse(HttpStatus.OK, trabajador);
	}
	
	/**
	 * Servicio para insertar nuevos trabajadores
	 * @param trabajador 
	 * @param result
	 * @return
	 */
	@PostMapping("/trabajadores")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@Valid @RequestBody Trabajador trabajador, BindingResult result) {
		Trabajador trabajadorNuevo = null;
		
		// Comprobación de que la petición tiene datos correctos
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			
			return createErrorResponse(HttpStatus.BAD_REQUEST, String.join(", ", errors));
		}
		
		try {
			// Comprobación de que el ID no se encuentra ya en la BBDD
			if (this.trabajadorService.findById(trabajador.getIdTrabajador()) != null) {
				return createErrorResponse(HttpStatus.CONFLICT,
						"El trabajador con el ID: ".concat(trabajador.getIdTrabajador()).concat(" ya existe."));
			}
			
			// Comprobación de que el DNI no se encuentra ya en la BBDD
			if (this.trabajadorService.findByDni(trabajador.getDni()) != null) {
				return createErrorResponse(HttpStatus.CONFLICT,
						"El trabajador con el DNI: ".concat(trabajador.getDni()).concat(" ya existe"));
			}
			
			// Comprobación de que el email no se encuentra ya en la BBDD
			if (this.trabajadorService.findByEmail(trabajador.getEmail()) != null) {
				return createErrorResponse(HttpStatus.CONFLICT, 
						"El trabajador con el correo: ".concat(trabajador.getEmail().concat(" ya existe")));
			}
			
			trabajadorNuevo = this.trabajadorService.save(trabajador);
		} catch (DataAccessException e) {
			return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error al realizar la inserción en la base de datos.");
		}
		
		return createResultResponse(HttpStatus.CREATED, trabajadorNuevo);
	}
	
	/**
	 * Servicio para actualizar un trabajador
	 * @param trabajador Los datos con la actualización del trabajador
	 * @param result
	 * @param id El ID del trabajador que se quiere actualizar
	 * @return Los nuevos datos del trabajador una vez están actualizados
	 */
	@PutMapping("/trabajadores/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> update(@Valid @RequestBody Trabajador trabajador, BindingResult result, @PathVariable String id) {
		Trabajador trabajadorUpdate = null;
		
		// Comprobación de que la petición tiene datos correctos
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			
			return createErrorResponse(HttpStatus.BAD_REQUEST, String.join(", ", errors));
		}
		
		try {
			Trabajador trabajadorActual = this.trabajadorService.findById(id);
			
			// Comprobación de que existe un trabajador con el ID de los parámetros
			if (trabajadorActual == null) {
				return createErrorResponse(HttpStatus.NOT_FOUND,
						"El trabajador con el ID: ".concat(id).concat(" no se ha encontrado."));
			}
			
			trabajadorActual.setNombre(trabajador.getNombre());
			trabajadorActual.setApellidos(trabajador.getApellidos());
			trabajadorActual.setEspecialidad(trabajador.getEspecialidad());
			trabajadorActual.setDni(trabajador.getDni());
			trabajadorActual.setEmail(trabajador.getEmail());
			trabajadorActual.setContraseña(trabajador.getContraseña());
			
			trabajadorUpdate = this.trabajadorService.save(trabajadorActual);
		} catch (DataAccessException e) {
			return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error al actualizar el trabajador en la base de datos.");
		}
		
		return createResultResponse(HttpStatus.CREATED, trabajadorUpdate);
	}
	
	/**
	 * Servicio para eliminar a un trabajador según su ID
	 * @param id El ID del trabajador a eliminar
	 * @return Una respuesta de éxito en caso de que se logre eliminar
	 */
	@DeleteMapping("/trabajadores/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> delete(@PathVariable String id) {
		try {
			// Comprobación de que existe un trabajador con el ID de los parámetros
			if (this.trabajadorService.findById(id) == null) {
				return createErrorResponse(HttpStatus.NOT_FOUND,
						"El trabajador con el ID: ".concat(id).concat(" no se ha encontrado."));
			}
			
			this.trabajadorService.delete(id);
		} catch (DataAccessException e) {
			return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error al eliminar el trabajador de la base de datos.");
		}
		
		return createResultResponse(HttpStatus.OK, "Trabajador eliminado con éxito.");
	}
}
