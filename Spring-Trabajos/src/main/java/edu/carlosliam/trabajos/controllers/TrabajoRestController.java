package edu.carlosliam.trabajos.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

import edu.carlosliam.trabajos.models.dto.TrabajoDTO;
import edu.carlosliam.trabajos.models.services.ITrabajadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
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

	@Autowired
	private ITrabajadorService trabajadorService;
	
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
		List<TrabajoDTO> trabajosDTO;

		try {
			trabajosDTO = this.trabajoService
					.findAll()
					.stream()
					.map(TrabajoDTO::convertToDTO)
					.collect(Collectors.toList());
		} catch (DataAccessException e) {
			return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error al realizar la consulta en la base de datos.");
		}

		return createResultResponse(HttpStatus.OK, trabajosDTO);
	}

	@GetMapping("/trabajos/sin_asignar")
	public ResponseEntity<?> showSinAsignar() {
		List<TrabajoDTO> trabajosDTO;

		try {
			trabajosDTO = this.trabajoService
					.findAll()
					.stream()
					.filter(t -> t.getTrabajador() == null)
					.map(TrabajoDTO::convertToDTO)
					.toList();
		} catch(DataAccessException e) {
			return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error al realizar la consulta en la base de datos.");
		}

		return createResultResponse(HttpStatus.OK, trabajosDTO);
	}

	/*
	 * Servicio para obtener un listado de trabajos finalizados
	 */
	@GetMapping("/trabajos/finalizados")
	public ResponseEntity<?> showFinalizados() {
		List<TrabajoDTO> trabajosDTO;

		try {
			trabajosDTO = this.trabajoService
					.findAll()
					.stream()
					.filter(t -> t.getFecFin() != null)
					.map(TrabajoDTO::convertToDTO)
					.toList();
		} catch(DataAccessException e) {
			return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error al realizar la consulta en la base de datos.");
		}

		return createResultResponse(HttpStatus.OK, trabajosDTO);
	}

	/*
	 * Servicio para obtener un listado de trabajos pendientes
	 */
	@GetMapping("/trabajos/pendientes")
	public ResponseEntity<?> showPendientes() {
		List<TrabajoDTO> trabajosDTO;

		try {
			trabajosDTO = this.trabajoService
					.findAll()
					.stream()
					.filter(t -> t.getTrabajador() != null)
					.filter(t -> t.getFecFin() == null)
					.map(TrabajoDTO::convertToDTO)
					.toList();
		} catch(DataAccessException e) {
			return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error al realizar la consulta en la base de datos.");
		}

		return createResultResponse(HttpStatus.OK, trabajosDTO);
	}
	
	/*
	 * Servicio para obtener un trabajo por su ID pasado por parámetro
	 */
	@GetMapping("/trabajos/{id}")
	public ResponseEntity<?> show(@PathVariable String id) {
		Trabajo trabajo;
				
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
		
		return createResultResponse(HttpStatus.OK, TrabajoDTO.convertToDTO(trabajo));
	}

	/*
	 * Servicio para obtener un listado de trabajos pendientes ordenados por prioridad
	 * de un trabajador pasado por parámetro
	 */
	@GetMapping("/trabajos/{trabajadorId}/prioridad")
	public ResponseEntity<?> tareasEmpleadoPorPrioridad(@PathVariable String trabajadorId) {
		List<TrabajoDTO> trabajosDTO;

		try {
			// Encontrar el trabajador por ID
			Trabajador trabajador = this.trabajadorService.findById(trabajadorId);
			if (trabajador == null) {
				return createErrorResponse(HttpStatus.NOT_FOUND,
						"El trabajador con el ID: ".concat(trabajadorId).concat(" no se ha encontrado."));
			}

			trabajosDTO = this.trabajoService
				.findAll()
				.stream()
				.filter(t -> t.getTrabajador() == trabajador)
				.filter(t -> t.getFecFin() == null)
				.sorted(Comparator.comparingInt(Trabajo::getPrioridad))
				.map(TrabajoDTO::convertToDTO)
				.toList();
		} catch (DataAccessException e) {
			return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error al realizar la consulta en la base de datos.");
		}

		return createResultResponse(HttpStatus.OK, trabajosDTO);
	}

	/*
	 * Servicio para obtener un listado de trabajos pendientes de una prioridad
	 * en concreto de un trabajador pasado por parámetro
	 */
	@GetMapping("/trabajos/{trabajadorId}/prioridad/{prioridad}")
	public ResponseEntity<?> tareasEmpleadoDeXPrioridad(@PathVariable String trabajadorId, @PathVariable String prioridad) {
		List<TrabajoDTO> trabajosDTO;

		try {
			// Encontrar el trabajador por ID
			Trabajador trabajador = this.trabajadorService.findById(trabajadorId);
			if (trabajador == null) {
				return createErrorResponse(HttpStatus.NOT_FOUND,
						"El trabajador con el ID: ".concat(trabajadorId).concat(" no se ha encontrado."));
			}

			trabajosDTO = this.trabajoService
					.findAll()
					.stream()
					.filter(t -> t.getTrabajador() == trabajador)
					.filter(t -> t.getFecFin() == null)
					.filter(t -> t.getPrioridad() == Integer.parseInt(prioridad))
					.sorted(Comparator.comparingInt(Trabajo::getPrioridad))
					.map(TrabajoDTO::convertToDTO)
					.toList();
		} catch (DataAccessException e) {
			return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error al realizar la consulta en la base de datos.");
		}

		return createResultResponse(HttpStatus.OK, trabajosDTO);
	}

	@GetMapping("/trabajos/{trabajadorId}/finalizados/{fecIni}/{fecFin}")
	public ResponseEntity<?> getTrabajosFinalizados(@PathVariable String trabajadorId, @PathVariable String fecIni,
													@PathVariable String fecFin) {
		List<TrabajoDTO> trabajos;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy");
		LocalDate inicio;
		LocalDate fin;

		try {
			inicio = LocalDate.parse(fecIni, formatter);
			fin = LocalDate.parse(fecFin, formatter);
		} catch (DateTimeParseException e) {
			return createErrorResponse(HttpStatus.BAD_REQUEST,
					"El formato de la fecha no es válido (Debe ser: d-MM-yyyy)");
		}

		if (inicio.isAfter(fin)) {
			return createErrorResponse(HttpStatus.BAD_REQUEST,
					"La fecha de inicio no puede ser posterior a la fecha de fin");
		}

		try {
			Trabajador trabajador = this.trabajadorService.findById(trabajadorId);

			if (trabajador == null) {
				return createErrorResponse(HttpStatus.NOT_FOUND,
						"El trabajador con el ID: ".concat(trabajadorId).concat(" no se ha encontrado."));
			}

			trabajos = new ArrayList<>(this.trabajoService
					.findAll()
					.stream()
					.filter(t -> t.getTrabajador() == trabajador)
					.filter(t -> t.getFecFin() != null)
					.filter(t -> t.getFecIni().isAfter(inicio) || t.getFecIni().isEqual(inicio))
					.filter(t -> t.getFecFin().isBefore(fin) || t.getFecFin().isEqual(fin))
					.map(TrabajoDTO::convertToDTO)
					.toList()
			);
		} catch(DataAccessException e) {
			return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error al realizar la consulta en la base de datos.");
		}

		return createResultResponse(HttpStatus.OK, trabajos);
	}
	
	/*
	 * Servicio para insertar un nuevo trabajo en la base de datos.
	 */
	@PostMapping("/trabajos")
	//@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@Valid @RequestBody Trabajo trabajo, BindingResult result) {
		Trabajo trabajoNuevo;
		
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
					"Error al realizar la inserción en la base de datos: " + e.getLocalizedMessage());
		}
		
		return createResultResponse(HttpStatus.CREATED, TrabajoDTO.convertToDTO(trabajoNuevo));
	}
	
	@PutMapping("/trabajos/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Trabajo trabajo, BindingResult result, @PathVariable String id) {
		Trabajo trabajoUpdate;
		
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
		
		return createResultResponse(HttpStatus.CREATED, TrabajoDTO.convertToDTO(trabajoUpdate));
	}

	@PutMapping("/trabajos/{trabajoId}/asignar/{trabajadorId}")
	public ResponseEntity<?> asignarTrabajo(@PathVariable String trabajoId, @PathVariable String trabajadorId) {
		Trabajo trabajoUpdate;

		try {
			// Encontrar el trabajo por ID
			Trabajo trabajoActual = this.trabajoService.findById(trabajoId);
			if (trabajoActual == null) {
				return createErrorResponse(HttpStatus.NOT_FOUND,
						"El trabajo con el ID: ".concat(trabajoId).concat(" no se ha encontrado."));
			}

			// Encontrar el trabajador por ID
			Trabajador trabajador = this.trabajadorService.findById(trabajadorId);
			if (trabajador == null) {
				return createErrorResponse(HttpStatus.NOT_FOUND,
						"El trabajador con el ID: ".concat(trabajadorId).concat(" no se ha encontrado."));
			}

			// Comprobar que la categoría del trabajo coincide con la especialidad del trabajador
			if (!trabajoActual.getCategoria().equals(trabajador.getEspecialidad())) {
				return createErrorResponse(HttpStatus.BAD_REQUEST,
						"La categoría del trabajo y la especialidad del trabajador no coinciden.");
			}

			// Asignar el trabajador al trabajo
			trabajoActual.setTrabajador(trabajador);

			// Guardar el trabajo actualizado
			trabajoUpdate = this.trabajoService.save(trabajoActual);
		} catch (DataAccessException e) {
			return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error al asignar el trabajador al trabajo en la base de datos.");
		}

		return createResultResponse(HttpStatus.CREATED, TrabajoDTO.convertToDTO(trabajoUpdate));
	}

	@PutMapping("/trabajos/{trabajoId}/finalizar/{actualDate}")
	public ResponseEntity<?> finalizarTrabajo(@PathVariable String trabajoId, @PathVariable String actualDate) {
		Trabajo trabajoUpdate;

		try {
			LocalDate actualDateParsed = LocalDate.parse(actualDate);
			// Encontrar el trabajo por ID
			Trabajo trabajoActual = this.trabajoService.findById(trabajoId);
			if (trabajoActual == null) {
				return createErrorResponse(HttpStatus.NOT_FOUND,
						"El trabajo con el ID: ".concat(trabajoId).concat(" no se ha encontrado."));
			}

			if (actualDateParsed.isBefore(trabajoActual.getFecIni())) {
				return createErrorResponse(HttpStatus.BAD_REQUEST,
						"La fecha de finalización debe ser posterior a la de inicio.");
			}

			// Asignar el trabajador al trabajo
			trabajoActual.setFecFin(actualDateParsed);

			// Guardar el trabajo actualizado
			trabajoUpdate = this.trabajoService.save(trabajoActual);
		} catch (DataAccessException e) {
			return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error al finalizar el trabajo.");
		}

		return createResultResponse(HttpStatus.CREATED, TrabajoDTO.convertToDTO(trabajoUpdate));
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

	@PostMapping("/trabajos/{trabajadorId}")
	public ResponseEntity<?> createWithTrabajador(@Valid @RequestBody Trabajo trabajo, BindingResult result,
												  @PathVariable String trabajadorId) {
		Trabajo trabajoNuevo;

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

			// Asignación del trabajador
			Trabajador trabajador = this.trabajadorService.findById(trabajadorId);
			if (trabajador == null) {
				return createErrorResponse(HttpStatus.NOT_FOUND,
						"El trabajador con el ID: ".concat(trabajadorId).concat(" no se ha encontrado."));
			}
			trabajo.setTrabajador(trabajador);

			trabajoNuevo = this.trabajoService.save(trabajo);
		} catch (DataAccessException e) {
			return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error al realizar la inserción en la base de datos.");
		}

		return createResultResponse(HttpStatus.CREATED, TrabajoDTO.convertToDTO(trabajo));
	}
}
