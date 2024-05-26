package edu.carlosliam.trabajos.controllers;

import edu.carlosliam.trabajos.models.entity.Trabajador;
import edu.carlosliam.trabajos.models.entity.Trabajo;
import edu.carlosliam.trabajos.models.services.TrabajadorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/app")
public class TrabajadorTempController {
    @Autowired
    TrabajadorServiceImpl trabajadorService = new TrabajadorServiceImpl();

    @GetMapping("/trabajador")
    public String trabajadores(Model model) {
        try {
            List<Trabajador> trabajadores = trabajadorService.findAll();

            model.addAttribute("trabajadores", trabajadores);
        } catch (Exception e) {
            model.addAttribute("error", "Error al obtener la lista de trabajadores: "
                    + e.getMessage());
        }
        return "trabajadores";
    }

    @GetMapping("/trabajador/{id}")
    public String trabajador(@PathVariable String id, Model model) {
        try {
            Trabajador trabajador = trabajadorService.findById(id);
            model.addAttribute("trabajador", trabajador);
        } catch (Exception e) {
            model.addAttribute("error", "Error al obtener el trabajador: "
                    + e.getMessage());
        }
        return "trabajador-detail";
    }

    @PostMapping("/trabajador/save")
    public String guardarTrabajo(@ModelAttribute Trabajador trabajador, Model model) {
        try {
            trabajadorService.save(trabajador);

            return "redirect:/app/trabajador";
        } catch (Exception e) {
            model.addAttribute("error", "Error al guardar el trabajador: "
                    + e.getMessage());
            return "trabajo";
        }
    }

    @GetMapping("/trabajador/delete/{id}")
    public String eliminarTrabajo(@PathVariable String id, Model model) {
        try {
            trabajadorService.delete(id);
            return "redirect:/app/trabajador";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al eliminar el trabajador.");
            return "trabajadores";
        }
    }

    // Renderiza la vista del formulario de edición de un trabajo
    @GetMapping("/trabajador/edit/{id}")
    public String editarTrabajo(@PathVariable String id, Model model) {
        try {
            Trabajador trabajador = trabajadorService.findById(id);
            model.addAttribute("trabajador", trabajador);
            return "trabajador-form"; // Vista del formulario de edición
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al actualizar el trabajador.");
            return "trabajadores";
        }
    }

    // Renderiza la vista del formulario de creación de un trabajo
    @GetMapping("/trabajador/new")
    public String nuevoTrabajo(Model model) {
        try {
            Trabajador trabajador = new Trabajador();
            model.addAttribute("trabajador", trabajador);
            return "trabajador-form";
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar el formulario para crear un nuevo trabajador.");
            return "trabajadores";
        }
    }
}
