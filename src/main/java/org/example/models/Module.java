package org.example.models;

import org.example.services.Shared;

public class Module extends Shared {
    private int coefficient;
    private int nombreHeure;
    private Semestre semestre;
    private Filiere filiere;

//    public Module(String libelle, int coefficient, int nombreHeure, Semestre semestre, Filiere filiere) {
//        super(libelle);
//        this.coefficient = coefficient;
//        this.nombreHeure = nombreHeure;
//        this.semestre = semestre;
//        this.filiere = filiere;
//    }
//
//    public Module() {
//        super();
//    }

    public int getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }

    public int getNombreHeure() {
        return nombreHeure;
    }

    public void setNombreHeure(int nombreHeure) {
        this.nombreHeure = nombreHeure;
    }

    public Semestre getSemestre() {
        return semestre;
    }

    public void setSemestre(Semestre semestre) {
        this.semestre = semestre;
    }

    public Filiere getFiliere() {
        return filiere;
    }

    public void setFiliere(Filiere filiere) {
        this.filiere = filiere;
    }
}
