package edu.carlosliam.trabajos.controllers;

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
@RequestMapping("/api")
public class TrabajadorRestController {
	
	@Autowired
	private ITrabajadorService trabajadorService;
	
	@GetMapping("/trabajadores")
	public List<Trabajador> index() {
		return this.trabajadorService.findAll();
	}
	
	@GetMapping("/trabajadores/{id}")
	public ResponseEntity<?> show(@PathVariable String id) {
		Trabajador trabajador = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			trabajador = this.trabajadorService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (trabajador == null) {
			response.put("mensaje", "El trabajador con el ID: ".concat(id).concat(" no se ha encontrado."));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Trabajador>(trabajador, HttpStatus.OK);
	}
	
	@PostMapping("/trabajadores")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@Valid @RequestBody Trabajador trabajador, BindingResult result) {
		Trabajador trabajadorNuevo = null;
		Map<String, Object> response = new HashMap<>();
		
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errores", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			trabajadorNuevo = this.trabajadorService.save(trabajadorNuevo);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la inserción en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El trabajador se ha creado con éxito.");
		response.put("trabajador", trabajadorNuevo);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/trabajadores/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> update(@Valid @RequestBody Trabajador trabajador, BindingResult result, @PathVariable String id) {
		Trabajador trabajadorActual = this.trabajadorService.findById(id);
		Trabajador trabajadorUpdate = null;
		
		Map<String, Object> response = new HashMap<>();
		
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errores", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (trabajadorActual == null) {
			response.put("mensaje", "El trabajador con el ID: ".concat(id).concat(" no se ha encontrado."));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			trabajadorActual.setNombre(trabajador.getNombre());
			trabajadorActual.setApellidos(trabajador.getApellidos());
			trabajadorActual.setEspecialidad(trabajador.getEspecialidad());
			trabajadorActual.setDni(trabajador.getDni());
			trabajadorActual.setEmail(trabajador.getEmail());
			trabajadorActual.setContraseña(trabajador.getContraseña());
			
			trabajadorUpdate = this.trabajadorService.save(trabajadorActual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el trabajador en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El trabajador ha sido actualizado con éxito.");
		response.put("trabajador", trabajadorUpdate);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/trabajadores/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> delete(@PathVariable String id) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			this.trabajadorService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el trabajador de la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El trabajador se ha eliminado con éxito.");
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
