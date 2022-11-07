package org.example.models;

import org.example.services.Shared;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Semestre extends Shared {
    private int numero;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private final List<Module> moduleList = new ArrayList<>();

    public Semestre(String libelle, int numero, LocalDate dateDebut, LocalDate dateFin) {
        super(libelle);
        this.numero = numero;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public Semestre() {
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public List<Module> getModuleList() {
        return moduleList;
    }
}
