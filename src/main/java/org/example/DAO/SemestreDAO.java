package org.example.DAO;

import org.example.services.DataBaseConnection;
import org.example.models.Semestre;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SemestreDAO {
    private final static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void add(Semestre semestre) {

        String query = "Insert into Semestre (id, libelle, numero, date_debut, date_fin) values(?,?,?,?,?)";

        try (PreparedStatement statement = DataBaseConnection.getInstance().prepareStatement(query)) {
            statement.setInt(1, semestre.getId());
            statement.setString(2, semestre.getLibelle());
            statement.setInt(3, semestre.getNumero());
            statement.setString(4, semestre.getDateDebut().format(dateFormatter));
            statement.setString(5, semestre.getDateFin().format(dateFormatter));

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void update(Semestre semestre) {

        String query = "update Semestre Set libelle = ?, numero = ?, date_debut = ?, date_fin = ? Where id = ?";

        try (PreparedStatement statement = DataBaseConnection.getInstance().prepareStatement(query)) {
            statement.setString(1, semestre.getLibelle());
            statement.setInt(2, semestre.getNumero());
            statement.setString(3, semestre.getDateDebut().format(dateFormatter));
            statement.setString(4, semestre.getDateFin().format(dateFormatter));
            statement.setInt(5, semestre.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void delete(int id) {
        String query = "delete Semestre Where id = ?";

        try (PreparedStatement statement = DataBaseConnection.getInstance().prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static Semestre getById(int id) {
        Semestre semestre = null;
        String query = "Select * From Semestre Where id = ?";

        try (PreparedStatement statement = DataBaseConnection.getInstance().prepareStatement(query)) {
            statement.setInt(1, id);

            ResultSet res = statement.executeQuery();
            if (res.next()) {
                semestre = new Semestre();
                semestre.setId(res.getInt("id"));
                semestre.setLibelle(res.getString("libelle"));
                semestre.setNumero(res.getInt("numero"));
                semestre.setDateDebut(LocalDate.parse(res.getString("date_debut"), dateFormatter));
                semestre.setDateFin(LocalDate.parse(res.getString("date_fin"), dateFormatter));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return semestre;
    }

    public static List<Semestre> getAll() {
        List<Semestre> semestreList = new ArrayList<>();
        String query = "Select * From Semestre";

        try (PreparedStatement statement = DataBaseConnection.getInstance().prepareStatement(query)) {

            ResultSet res = statement.executeQuery();

            while (res.next()) {
                Semestre semestre = new Semestre();

                semestre.setId(res.getInt("id"));
                semestre.setLibelle(res.getString("libelle"));
                semestre.setNumero(res.getInt("numero"));
                semestre.setDateDebut(LocalDate.parse(res.getString("date_debut"), dateFormatter));
                semestre.setDateFin(LocalDate.parse(res.getString("date_fin"), dateFormatter));

                semestreList.add(semestre);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return semestreList;
    }
}
