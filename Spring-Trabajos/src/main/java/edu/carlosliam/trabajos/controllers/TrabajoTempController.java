package edu.carlosliam.trabajos.controllers;

import edu.carlosliam.trabajos.models.dto.TrabajadorDTO;
import edu.carlosliam.trabajos.models.dto.TrabajadorInTrabajoDTO;
import edu.carlosliam.trabajos.models.dto.TrabajoDTO;
import edu.carlosliam.trabajos.models.entity.Trabajador;
import edu.carlosliam.trabajos.models.entity.Trabajo;
import edu.carlosliam.trabajos.models.services.TrabajadorServiceImpl;
import edu.carlosliam.trabajos.models.services.TrabajoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/app")
public class TrabajoTempController {

    @Autowired
    TrabajoServiceImpl trabajoService = new TrabajoServiceImpl();

    @Autowired
    TrabajadorServiceImpl trabajadorService = new TrabajadorServiceImpl();

    @GetMapping("/trabajo")
    public String trabajos(Model model) {
        try {
            List<TrabajoDTO> trabajosDTO = trabajoService.findAll()
                    .stream()
                    .map(TrabajoDTO::convertToDTO)
                    .collect(Collectors.toList());

            List<TrabajadorDTO> trabajadoresDTO = trabajadorService.findAll()
                    .stream()
                    .map(TrabajadorDTO::convertToDTO)
                    .collect(Collectors.toList());

            model.addAttribute("trabajos", trabajosDTO);
            model.addAttribute("trabajadores", trabajadoresDTO);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al obtener la lista de trabajos.");
        }
        return "trabajo";
    }

    @PostMapping("/trabajo/save")
    public String guardarTrabajo(@ModelAttribute Trabajo trabajo, Model model) {
        try {
            trabajoService.save(trabajo);

            return "redirect:/app/trabajo";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al guardar el trabajo.");
            return "trabajo"; // Vuelve a mostrar el formulario con el mensaje de error
        }
    }

    @GetMapping("/trabajo/delete/{id}")
    public String eliminarTrabajo(@PathVariable String id, Model model) {
        try {
            trabajoService.delete(id);
            return "redirect:/app/trabajo";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al eliminar el trabajo.");
            return "trabajo"; // redirigir a la lista de trabajos en caso de error
        }
    }
}