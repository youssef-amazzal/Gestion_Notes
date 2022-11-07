package org.example.DAO;

import org.example.services.DataBaseConnection;
import org.example.models.Filiere;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FiliereDAO {
    public static void add(Filiere filiere) {

        String query = "Insert into Filiere (id, libelle) values(?,?)";

        try (PreparedStatement statement = DataBaseConnection.getInstance().prepareStatement(query)) {
            statement.setInt(1, filiere.getId());
            statement.setString(2, filiere.getLibelle());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void update(Filiere filiere) {

        String query = "update Filiere Set libelle = ? Where id = ?";

        try (PreparedStatement statement = DataBaseConnection.getInstance().prepareStatement(query)) {
            statement.setString(1, filiere.getLibelle());
            statement.setInt(2, filiere.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void delete(int id) {
        String query = "delete Filiere Where id = ?";

        try (PreparedStatement statement = DataBaseConnection.getInstance().prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static Filiere getById(int id) {
        Filiere filiere = null;
        String query = "Select * From Filiere Where id = ?";

        try (PreparedStatement statement = DataBaseConnection.getInstance().prepareStatement(query)) {
            statement.setInt(1, id);

            ResultSet res = statement.executeQuery();
            if (res.next()) {
                filiere = new Filiere();
                filiere.setId(res.getInt("id"));
                filiere.setLibelle(res.getString("libelle"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return filiere;
    }

    public static List<Filiere> getAll() {
        List<Filiere> filiereList = new ArrayList<>();
        String query = "Select * From Filiere";

        try (PreparedStatement statement = DataBaseConnection.getInstance().prepareStatement(query)) {

            ResultSet res = statement.executeQuery();

            while (res.next()) {
                Filiere filiere = new Filiere();
                filiere.setId(res.getInt("id"));
                filiere.setLibelle(res.getString("libelle"));

                filiereList.add(filiere);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return filiereList;
    }
}
