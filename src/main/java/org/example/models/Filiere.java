package org.example.models;

import org.example.services.Shared;

import java.util.ArrayList;
import java.util.List;

public class Filiere extends Shared {
    private final List<Module> moduleList = new ArrayList<>();

    public Filiere(String libelle) {
        super(libelle);
    }

    public Filiere() {
    }

    public List<Module> getModuleList() {
        return moduleList;
    }
}
