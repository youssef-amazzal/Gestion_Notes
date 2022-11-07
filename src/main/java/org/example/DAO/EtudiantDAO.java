package org.example.DAO;

import org.example.services.DataBaseConnection;
import org.example.models.Etudiant;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EtudiantDAO {
    private final static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void add(Etudiant etudiant) {

        String query = "Insert into Etudiant (id, nom, prenom, date_naissance) values(?,?,?,?)";

        try (PreparedStatement statement = DataBaseConnection.getInstance().prepareStatement(query)) {
            statement.setInt(1, etudiant.getId());
            statement.setString(2, etudiant.getNom());
            statement.setString(3, etudiant.getPrenom());
            statement.setString(4, etudiant.getDateNaissance().format(dateFormatter));

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void update(Etudiant etudiant) {

        String query = "update Etudiant Set nom = ?, prenom = ?, date_naissance = ? Where id = ?";

        try (PreparedStatement statement = DataBaseConnection.getInstance().prepareStatement(query)) {
            statement.setString(1, etudiant.getNom());
            statement.setString(2, etudiant.getPrenom());
            statement.setString(3, etudiant.getDateNaissance().format(dateFormatter));
            statement.setInt(4, etudiant.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void delete(int id) {
        String query = "delete Etudiant Where id = ?";

        try (PreparedStatement statement = DataBaseConnection.getInstance().prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static Etudiant getById(int id) {
        Etudiant etudiant = null;
        String query = "Select * From Etudiant Where id = ?";

        try (PreparedStatement statement = DataBaseConnection.getInstance().prepareStatement(query)) {
            statement.setInt(1, id);

            ResultSet res = statement.executeQuery();
            if (res.next()) {
                etudiant = new Etudiant();
                etudiant.setId(res.getInt("id"));
                etudiant.setNom(res.getString("nom"));
                etudiant.setPrenom(res.getString("prenom"));
                etudiant.setDateNaissance(LocalDate.parse(res.getString("date_naissance"), dateFormatter));
//                System.out.println(res.getDate("date_naissance"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return etudiant;
    }

    public static List<Etudiant> getAll() {
        List<Etudiant> etudiantList = new ArrayList<>();
        String query = "Select * From Etudiant";

        try (PreparedStatement statement = DataBaseConnection.getInstance().prepareStatement(query)) {

            ResultSet res = statement.executeQuery();

            while (res.next()) {
                Etudiant etudiant = new Etudiant();
                etudiant.setId(res.getInt("id"));
                etudiant.setNom(res.getString("nom"));
                etudiant.setPrenom(res.getString("prenom"));
                etudiant.setDateNaissance(LocalDate.parse(res.getString("date_naissance"), dateFormatter));

                etudiantList.add(etudiant);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return etudiantList;
    }

}
