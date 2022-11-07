package org.example.services;

import org.example.models.Etudiant;
import org.example.models.Filiere;
import org.example.models.Semestre;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GestionNoteManager {
    private static final String path = GestionNoteManager.class.getResource("/").getPath().substring(1) + "\\";
    private static final String fileTemplate;
    static {
        try {
            fileTemplate = Files.readString(Path.of(GestionNoteManager.class.getResource("/templates/template.html").getPath().substring(1)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void generateHTML(Etudiant etudiant) {

        String fileName = String.format("%s_%s_%s.html", etudiant.getId(), etudiant.getNom(), etudiant.getPrenom());
        File file = new File(path + fileName);


        try (FileWriter fileWriter = new FileWriter(file))
        {

            String reportCard = fileTemplate.replace("$body", etudiant.getHTML());

            fileWriter.write(reportCard);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void generateHTML(List<Etudiant> etudiantList) {

        File file = new File(path + "allEtudiant.html");


        try (FileWriter fileWriter = new FileWriter(file))
        {

            StringBuilder allReportCards = new StringBuilder();
            for (Etudiant etudiant : etudiantList) {
                allReportCards.append(etudiant.getHTML()).append("<br><br>");
            }

            fileWriter.write(fileTemplate.replace("$body", allReportCards.toString()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Etudiant getMajorPromo() {
        Etudiant etudiant = null;
        String query =
                "SELECT id, nom, prenom, date_naissance, MAX(moyenne) "                                                             +
                        "FROM"                                                                                                  +
                        "(  SELECT id, nom, prenom, date_naissance, SUM(ex.note * md.coefficient) / SUM(md.coefficient) moyenne"    +
                        "   FROM Etudiant etu, Examen ex, Module md"                                                            +
                        "   WHERE etu.id = ex.id_etudiant"                                                                      +
                        "   AND ex.id_module = md.id"                                                                           +
                        "   GROUP BY ex.id_etudiant"                                                                            +
                        ");";

        try (PreparedStatement statement = DataBaseConnection.getInstance().prepareStatement(query)) {

            ResultSet res = statement.executeQuery();
            if (res.next()) {
                etudiant = new Etudiant();
                etudiant.setId(res.getInt("id"));
                etudiant.setNom(res.getString("nom"));
                etudiant.setPrenom(res.getString("prenom"));

                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                etudiant.setDateNaissance(LocalDate.parse(res.getString("date_naissance"), dateFormatter));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return etudiant;
    }

    public static void getMoyenneEtudiant() {
        File file = new File(path + "moyenneEtudiant.html");

        String tabletemplate = "" +
                                "\t<table>\n" +
                                "\t\t<thead>\n" +
                                "\t\t\t<tr> <th>nom</th> <th>prenom</th> <th>moyenne</th> </tr>\n" +
                                "\t\t</thead>\n" +
                                "\t\t<tbody>$rows\n\t\t</tbody>\n" +
                                "\t</table>";
        String rowtemplate = "\n\t\t\t<tr> <td>$nom</td> <td>$prenom</td> <td>$moyenne</td> </tr>";
        String rows = "";

        String query =  "SELECT nom, prenom, SUM(ex.note * md.coefficient) / SUM(md.coefficient) moyenne\n" +
                        "FROM Etudiant etu, Examen ex, Module md\n"                                         +
                        "WHERE etu.id = ex.id_etudiant\n"                                                   +
                        "\tAND ex.id_module = md.id\n"                                                      +
                        "GROUP BY ex.id_etudiant;"                                                          ;

        try (
                PreparedStatement statement = DataBaseConnection.getInstance().prepareStatement(query);
                FileWriter fileWriter = new FileWriter(file)
        ) {


            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                rows = rows + rowtemplate.replace("$nom", rs.getString("nom"));
                rows = rows.replace("$prenom", rs.getString("prenom"));
                rows = rows.replace("$moyenne", rs.getString("moyenne"));
            }


            fileWriter.write(fileTemplate.replace("$body",tabletemplate.replace("$rows", rows)));

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getMoyenneModule() {

        File file = new File(path + " moyenneModule.html");

        String tabletemplate = "" +
                "\t<table>\n" +
                "\t\t<thead>\n" +
                "\t\t\t<tr> <th>Module</th> <th>moyenne</th> </tr>\n" +
                "\t\t</thead>\n" +
                "\t\t<tbody>$rows\n\t\t</tbody>\n" +
                "\t</table>";
        String rowtemplate = "\n\t\t\t<tr> <td>$module</td> <td>$moyenne</td> </tr>";
        String rows = "";

        String query =
                "SELECT md.libelle module, SUM(ex.note * md.coefficient) / SUM(md.coefficient) moyenne "     +
                "FROM Etudiant etu, Examen ex, Module md "                                                   +
                "WHERE etu.id = ex.id_etudiant "                                                             +
                "   AND ex.id_module = md.id "                                                               +
                "GROUP BY ex.id_module;"                                                                    ;

        try (
                PreparedStatement statement = DataBaseConnection.getInstance().prepareStatement(query);
                FileWriter fileWriter = new FileWriter(file)
        ) {


            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                rows = rows + rowtemplate.replace("$module", rs.getString("module"));
                rows = rows.replace("$moyenne", rs.getString("moyenne"));
            }


            fileWriter.write(fileTemplate.replace("$body",tabletemplate.replace("$rows", rows)));

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

    }

}
