package repository;

import entities.Facture;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FactureRepository {

    public void ajouter(Facture facture) {
        String sql = "INSERT INTO Facture (date, total, id_client) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setDate(1, facture.getDate());
            stmt.setInt(2, facture.getTotal());
            if (facture.getId_client() != null) {
                stmt.setInt(3, facture.getId_client());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }

            stmt.executeUpdate();

            // Récupérer l'id généré automatiquement
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    facture.setId_facture(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifier(Facture facture) {
        String sql = "UPDATE Facture SET date=?, total=?, id_client=? WHERE id_facture=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, facture.getDate());
            stmt.setInt(2, facture.getTotal());
            if (facture.getId_client() != null) {
                stmt.setInt(3, facture.getId_client());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }
            stmt.setInt(4, facture.getId_facture());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void supprimer(int id) {
        String sql = "DELETE FROM Facture WHERE id_facture=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Facture trouverParId(int id) {
        String sql = "SELECT * FROM Facture WHERE id_facture=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Facture facture = new Facture();
                facture.setId_facture(rs.getInt("id_facture"));
                facture.setDate(rs.getDate("date"));
                facture.setTotal(rs.getInt("total"));
                int clientId = rs.getInt("id_client");
                if (rs.wasNull()) {
                    facture.setId_client(null);
                } else {
                    facture.setId_client(clientId);
                }
                return facture;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Facture> lister() {
        List<Facture> liste = new ArrayList<>();
        String sql = "SELECT * FROM Facture";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Facture facture = new Facture();
                facture.setId_facture(rs.getInt("id_facture"));
                facture.setDate(rs.getDate("date"));
                facture.setTotal(rs.getInt("total"));
                int clientId = rs.getInt("id_client");
                if (rs.wasNull()) {
                    facture.setId_client(null);
                } else {
                    facture.setId_client(clientId);
                }
                liste.add(facture);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }
}
