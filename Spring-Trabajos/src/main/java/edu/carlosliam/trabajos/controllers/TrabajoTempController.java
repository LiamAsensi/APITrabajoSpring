package edu.carlosliam.trabajos.controllers;

import edu.carlosliam.trabajos.models.dto.TrabajoDTO;
import edu.carlosliam.trabajos.models.entity.Trabajo;
import edu.carlosliam.trabajos.models.services.TrabajoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/app")
public class TrabajoTempController {

    @Autowired
    TrabajoServiceImpl trabajoService = new TrabajoServiceImpl();
    @GetMapping({"/trabajo"})
    public String trabajos(Model model) {
        List<TrabajoDTO> trabajosDTO;

        try {
            trabajosDTO = trabajoService.findAll()
                    .stream()
                    .map(TrabajoDTO::convertToDTO)
                    .collect(Collectors.toList());

            model.addAttribute("trabajos", trabajosDTO);
        } catch (Exception e) {
            e.printStackTrace();

            model.addAttribute("error", "Error al obtener la lista de trabajos.");
        }
        return "trabajo";
    }
}