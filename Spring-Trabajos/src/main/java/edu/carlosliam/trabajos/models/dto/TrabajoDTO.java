package edu.carlosliam.trabajos.models.dto;

import edu.carlosliam.trabajos.models.entity.Trabajo;

import java.time.LocalDate;

public class TrabajoDTO {
    private String codTrab;
    private String categoria;
    private String descripcion;
    private LocalDate fecIni;
    private LocalDate fecFin;
    private Float tiempo;
    private int prioridad;
    private TrabajadorInTrabajoDTO trabajador;

    public String getCodTrab() {
        return codTrab;
    }

    public void setCodTrab(String codTrab) {
        this.codTrab = codTrab;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecIni() {
        return fecIni;
    }

    public void setFecIni(LocalDate fecIni) {
        this.fecIni = fecIni;
    }

    public LocalDate getFecFin() {
        return fecFin;
    }

    public void setFecFin(LocalDate fecFin) {
        this.fecFin = fecFin;
    }

    public Float getTiempo() {
        return tiempo;
    }

    public void setTiempo(Float tiempo) {
        this.tiempo = tiempo;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public TrabajadorInTrabajoDTO getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(TrabajadorInTrabajoDTO trabajador) {
        this.trabajador = trabajador;
    }

    public static TrabajoDTO convertToDTO(Trabajo trabajo) {
        TrabajoDTO trabajoDTO = new TrabajoDTO();

        trabajoDTO.setCodTrab(trabajo.getCodTrab());
        trabajoDTO.setCategoria(trabajo.getCategoria());
        trabajoDTO.setDescripcion(trabajo.getDescripcion());
        trabajoDTO.setFecIni(trabajo.getFecIni());

        if (trabajo.getFecFin() != null) {
            trabajoDTO.setFecFin(trabajo.getFecFin());
        }

        if (trabajo.getTiempo() != null) {
            trabajoDTO.setTiempo(trabajo.getTiempo());
        }

        trabajoDTO.setPrioridad(trabajo.getPrioridad());

        if (trabajo.getTrabajador() != null) {
            trabajoDTO.setTrabajador(TrabajadorInTrabajoDTO.convertToDTO(trabajo.getTrabajador()));
        }

        return trabajoDTO;
    }

    public static Trabajo convertToEntity(TrabajoDTO trabajoDTO) {
        Trabajo trabajo = new Trabajo();

        trabajo.setCodTrab(trabajoDTO.getCodTrab());
        trabajo.setCategoria(trabajoDTO.getCategoria());
        trabajo.setDescripcion(trabajoDTO.getDescripcion());
        trabajo.setFecIni(trabajoDTO.getFecIni());

        if (trabajoDTO.getFecFin() != null) {
            trabajo.setFecFin(trabajoDTO.getFecFin());
        }

        if (trabajoDTO.getTiempo() != null) {
            trabajo.setTiempo(trabajoDTO.getTiempo());
        }

        trabajo.setPrioridad(trabajoDTO.getPrioridad());

        if (trabajoDTO.getTrabajador() != null) {
            trabajo.setTrabajador(TrabajadorInTrabajoDTO.convertToEntity(trabajoDTO.getTrabajador()));
        }

        return trabajo;
    }
}
