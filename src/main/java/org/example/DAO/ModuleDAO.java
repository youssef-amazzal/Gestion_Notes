package org.example.DAO;

import org.example.services.DataBaseConnection;
import org.example.models.Module;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModuleDAO {

    public static void add(Module module) {

        String query = "Insert into Module (id, libelle, coefficient, nombre_heure, id_filiere, id_semestre) values(?,?,?,?,?,?)";

        try (PreparedStatement statement = DataBaseConnection.getInstance().prepareStatement(query)) {
            statement.setInt(1, module.getId());
            statement.setString(2, module.getLibelle());
            statement.setInt(3, module.getCoefficient());
            statement.setInt(4, module.getNombreHeure());
            statement.setInt(5, module.getFiliere().getId());
            statement.setInt(6, module.getSemestre().getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void update(Module module) {

        String query = "update Module Set libelle = ?, coefficient = ?, nombre_heure = ?, id_filiere = ?, id_semestre = ? Where id = ?";

        try (PreparedStatement statement = DataBaseConnection.getInstance().prepareStatement(query)) {
            statement.setString(1, module.getLibelle());
            statement.setInt(2, module.getCoefficient());
            statement.setInt(3, module.getNombreHeure());
            statement.setInt(4, module.getFiliere().getId());
            statement.setInt(5, module.getSemestre().getId());
            statement.setInt(6, module.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void delete(int id) {
        String query = "delete Module Where id = ?";

        try (PreparedStatement statement = DataBaseConnection.getInstance().prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static Module getById(int id) {
        Module module = null;
        String query = "Select * From Module Where id = ?";

        try (PreparedStatement statement = DataBaseConnection.getInstance().prepareStatement(query)) {
            statement.setInt(1, id);

            ResultSet res = statement.executeQuery();
            if (res.next()) {
                module = new Module();
                module.setId(res.getInt("id"));
                module.setLibelle(res.getString("libelle"));
                module.setCoefficient(res.getInt("coefficient"));
                module.setNombreHeure(res.getInt("nombre_heure"));
                module.setFiliere(FiliereDAO.getById(res.getInt("id_filiere")));
                module.setSemestre(SemestreDAO.getById(res.getInt("id_semestre")));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return module;
    }

    public static List<Module> getAll() {
        List<Module> moduleList = new ArrayList<>();
        String query = "Select * From Module";

        try (PreparedStatement statement = DataBaseConnection.getInstance().prepareStatement(query)) {

            ResultSet res = statement.executeQuery();

            while (res.next()) {
                Module module = new Module();
                module.setId(res.getInt("id"));
                module.setLibelle(res.getString("libelle"));
                module.setCoefficient(res.getInt("coefficient"));
                module.setNombreHeure(res.getInt("nombre_heure"));
                module.setFiliere(FiliereDAO.getById(res.getInt("id_filiere")));
                module.setSemestre(SemestreDAO.getById(res.getInt("id_semestre")));

                moduleList.add(module);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return moduleList;
    }

}
