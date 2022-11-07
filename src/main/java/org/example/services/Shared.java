package org.example.services;

public abstract class Shared {
    protected int id;
    protected String libelle;

    protected Shared(String libelle) {
        this.id = IdGenerator.getId();
        this.libelle = libelle;
    }

    public Shared() {};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
