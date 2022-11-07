package org.example.DAO;

import org.example.models.Semestre;
import org.example.services.DataBaseConnection;
import org.example.models.Etudiant;
import org.example.models.Examen;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExamenDAO {
    public static void add(Examen examen) {

        String query = "Insert into Examen (note, id_etudiant, id_module) values(?,?,?)";

        try (PreparedStatement statement = DataBaseConnection.getInstance().prepareStatement(query)) {
            statement.setDouble(1, examen.getNote());
            statement.setInt(2, examen.getEtudiant().getId());
            statement.setInt(3, examen.getModule().getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void update(Examen examen) {

        String query = "update Examen Set note = ? Where id_etudiant = ? AND id_module = ?";

        try (PreparedStatement statement = DataBaseConnection.getInstance().prepareStatement(query)) {
            statement.setDouble(1, examen.getNote());
            statement.setInt(2, examen.getEtudiant().getId());
            statement.setInt(3, examen.getModule().getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void delete(int idEtudiant, int idModule) {
        String query = "delete Examen Where id_etudiant = ? AND id_module = ?";

        try (PreparedStatement statement = DataBaseConnection.getInstance().prepareStatement(query)) {
            statement.setInt(1, idEtudiant);
            statement.setInt(2, idModule);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static Examen getById(int idEtudiant, int idModule) {
        Examen examen = null;
        String query = "Select * From Examen Where id_etudiant = ? AND id_module = ?";

        try (PreparedStatement statement = DataBaseConnection.getInstance().prepareStatement(query)) {
            statement.setInt(1, idEtudiant);
            statement.setInt(2, idModule);

            ResultSet res = statement.executeQuery();
            if (res.next()) {
                examen = new Examen();
                examen.setNote(res.getInt("note"));
                examen.setEtudiant(EtudiantDAO.getById(res.getInt("id_etudiant")));
                examen.setModule(ModuleDAO.getById(res.getInt("id_module")));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return examen;
    }

    public static List<Examen> getAll() {
        List<Examen> examenList = new ArrayList<>();
        String query = "Select * From Examen";

        try (PreparedStatement statement = DataBaseConnection.getInstance().prepareStatement(query)) {

            ResultSet res = statement.executeQuery();

            while (res.next()) {
                Examen examen = new Examen();
                examen.setNote(res.getInt("note"));
                examen.setEtudiant(EtudiantDAO.getById(res.getInt("id_etudiant")));
                examen.setModule(ModuleDAO.getById(res.getInt("id_module")));

                examenList.add(examen);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return examenList;
    }

    public static List<Examen> getForStudent(Etudiant etudiant) {
        List<Examen> examenList = new ArrayList<>();
        String query = "Select * From Examen Where id_etudiant = ?";

        try (PreparedStatement statement = DataBaseConnection.getInstance().prepareStatement(query)) {
            statement.setInt(1, etudiant.getId());

            ResultSet res = statement.executeQuery();

            while (res.next()) {
                Examen examen = new Examen();
                examen.setNote(res.getInt("note"));
                examen.setEtudiant(EtudiantDAO.getById(res.getInt("id_etudiant")));
                examen.setModule(ModuleDAO.getById(res.getInt("id_module")));

                examenList.add(examen);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return examenList;
    }

    public static List<Examen> getBy(Etudiant etudiant, Semestre semestre) {
        List<Examen> examenList = new ArrayList<>();
        String query =
                "SELECT * "                                 +
                "FROM Etudiant, Examen, Module, semestre "  +
                "WHERE "                                    +
                "       Etudiant.id = Examen.id_etudiant "  +
                "   AND Examen.id_module = Module.id "      +
                "   AND Module.id_semestre = semestre.id "  +
                "   AND semestre.numero = ? "               +
                "   AND Etudiant.id = ?;";

        try (PreparedStatement statement = DataBaseConnection.getInstance().prepareStatement(query)) {
            statement.setInt(1, etudiant.getId());

            ResultSet res = statement.executeQuery();

            while (res.next()) {
                Examen examen = new Examen();
                examen.setNote(res.getInt("note"));
                examen.setEtudiant(EtudiantDAO.getById(res.getInt("id_etudiant")));
                examen.setModule(ModuleDAO.getById(res.getInt("id_module")));

                examenList.add(examen);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return examenList;
    }

    
}
