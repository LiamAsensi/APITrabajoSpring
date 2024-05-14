package edu.carlosliam.trabajos.models.dto;

import edu.carlosliam.trabajos.models.entity.Trabajo;

import java.time.LocalDate;

public class TrabajoInTrabajadorDTO {
    private String codTrab;
    private String categoria;
    private String descripcion;
    private LocalDate fecIni;
    private LocalDate fecFin;
    private Float tiempo;
    private int prioridad;

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

    public static TrabajoInTrabajadorDTO convertToDTO(Trabajo trabajo) {
        TrabajoInTrabajadorDTO trabajoDTO = new TrabajoInTrabajadorDTO();

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

        return trabajoDTO;
    }
}
