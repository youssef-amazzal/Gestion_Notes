package org.example.models;

import org.example.DAO.ExamenDAO;
import org.example.DAO.SemestreDAO;
import org.example.services.DataBaseConnection;
import org.example.services.HTML_Exporter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Etudiant implements HTML_Exporter {
    private int id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private final List<Examen> examenList = new ArrayList<>();

    public Etudiant(String nom, String prenom, LocalDate dateNaissance) {
        this.id = 0;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
    }

    public Etudiant() {};


    public double calculerNoteSemestre(int numeroSemestre) {
        double noteSemestre;

        String query =   "Select SUM(ex.note * md.coefficient) / SUM(md.coefficient) "  +
                            "FROM Examen ex, Module md, Semestre sem "                  +
                            "WHERE ex.id_etudiant = ? "                                 +
                            "AND ex.id_module = md.id "                                 +
                            "AND md.id_semestre = sem.id "                              +
                            "AND sem.numero = ?;"                                       ;

        try (PreparedStatement statement = DataBaseConnection.getInstance().prepareStatement(query)) {
            statement.setInt(1, this.id);
            statement.setInt(2, numeroSemestre);

            noteSemestre = statement.executeQuery().getDouble(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return noteSemestre;
    }

    @Override
    public String getHTML() {

        String reportCard;

        try {

            reportCard = this.getInfoHTML() + "<br>" +this.getAllSemesterHTML();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return reportCard;
    }
    public String getInfoHTML() throws IOException {
        //substring used to change path from: /C:/ --> C:/
        String etudiantInfo = Files.readString(Path.of(getClass().getResource("/templates/etudiant_info_template.html").getPath().substring(1)));
        etudiantInfo = etudiantInfo.replace("$id_etudiant", String.valueOf(this.id)).replace("$nom_etudiant", this.nom + " " + this.prenom);

        return etudiantInfo;
    }

    public String getAllSemesterHTML() throws IOException {
        //substring used to change path from: /C:/ --> C:/
        String semesterTableTemplate = Files.readString(Path.of(getClass().getResource("/templates/semestre_template.html").getPath().substring(1)));
        String rowTemplate = Files.readString(Path.of(getClass().getResource("/templates/table_row.html").getPath().substring(1)));


        // Filling the semester table
        StringBuilder allSemesterTables = new StringBuilder();

        for (Semestre semestre : SemestreDAO.getAll()) {
            StringBuilder tableRows = new StringBuilder();

            for (Examen examen : ExamenDAO.getBy(this, semestre)) {
                String row = rowTemplate;
                row = row.replace("$module", examen.getModule().getLibelle());
                row = row.replace("$note", String.valueOf(examen.getNote()));
                row = row.replace("$decision", (examen.getNote() >= 12)? "V" : "NV");
                tableRows.append(row).append("\n");
            }

            String semesterTable = semesterTableTemplate.replace("$semestre_numero", String.valueOf(semestre.getNumero()));
            semesterTable = semesterTable.replace("$table_rows", tableRows.toString());

            allSemesterTables.append(semesterTable).append("\n");
        }

        return allSemesterTables.toString();
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public List<Examen> getExamenList() {
        return examenList;
    }
}
