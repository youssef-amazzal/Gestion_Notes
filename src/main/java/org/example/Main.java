package org.example;

import org.example.DAO.EtudiantDAO;
import org.example.DAO.FiliereDAO;
import org.example.DAO.SemestreDAO;
import org.example.models.Etudiant;
import org.example.models.Semestre;
import org.example.services.DataBaseConnection;
import org.example.services.GestionNoteManager;
import org.example.services.ScriptRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;
public class Main {
    public static void main(String[] args) {
        DataBaseConnection.getInstance();
        ScriptRunner.runScript("/script.sql");

        GestionNoteManager.generateHTML(EtudiantDAO.getAll());
        GestionNoteManager.getMoyenneEtudiant();
        GestionNoteManager.getMoyenneModule();
        Etudiant etudiant = GestionNoteManager.getMajorPromo();
        System.out.println(etudiant.getNom() + " " + etudiant.getPrenom() + " " + etudiant.getDateNaissance());

//        showEtudiantTable();

    }

    private static int showMainMenu() {
        System.out.println("\033[2J");
        System.out.printf("[1] %-15s\t[2] %-15s\n\n[3] %-15s\t[4] %-15s\n\n[5] %-15s\t[0] %-15s\n\n\n\n",
                "Etudiant","Module"     ,
                "Filiere","Semestre"    ,
                "Examen","exit")        ;

        String choice;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {

            do {
                System.out.print("choice: ");

            } while (!(choice = bufferedReader.readLine().trim()).matches("[0-5]"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        return Integer.parseInt(choice);
    }

    private static void showEtudiantTable() {
        System.out.println("\033[2J");
        System.out.printf("[1] %-15s\t[2] %-15s\n\n[3] %-15s\t[4] %-15s\n\n[0] %-15s\n\n\n\n",
                "Get","Add"     ,
                "Update","Delete"    ,
                "Exit")        ;

        String choice;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {

            do {
                System.out.print("choice: ");

            } while (!(choice = bufferedReader.readLine().trim()).matches("[0-4]"));


            switch (Integer.parseInt(choice)) {

                case 0:
                    return;
                case 1:
                    System.out.println("enter an ID to get specific row or * to get All");
                    do {
                        System.out.print("ID etudiant: ");

                    } while (!(choice = bufferedReader.readLine().trim()).matches("\\d+|[*]"));

                    System.out.printf("\t%-20s |\t%-20s |\t%-20s |\t%-20s\n","id","nom","prenom","date_naissance");
                    for (int i = 0; i < 100; i++) {
                        System.out.print("=");
                    }


                    if (choice.equals("*")) {
                        EtudiantDAO.getAll().forEach(etudiant -> {
                            System.out.printf("\n\t%-20s |\t%-20s |\t%-20s |\t%-20s",etudiant.getId(),etudiant.getNom(),etudiant.getPrenom(),etudiant.getDateNaissance().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                        });
                    } else {
                        Etudiant etudiant = EtudiantDAO.getById(Integer.parseInt(choice));
                        System.out.printf("\n\t%-20s |\t%-20s |\t%-20s |\t%-20s",etudiant.getId(),etudiant.getNom(),etudiant.getPrenom(),etudiant.getDateNaissance().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    }

                    break;

                case 2:
                    ;



            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}