package org.example.models;

public class Examen {
    private double note;
    private Module module;
    private Etudiant etudiant;

    public Examen(double note, Module module, Etudiant etudiant) {
        this.note = note;
        this.module = module;
        this.etudiant = etudiant;
    }

    public Examen() {
    }

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }
}
