package repository;

import entities.Facture;
import entities.LigneFacture;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FactureRepository {

    public void ajouter(Facture facture) {
        String sql = "INSERT INTO Facture (date, total, id_client) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setDate(1, facture.getDate());
            stmt.setDouble(2, facture.getTotal());
            if (facture.getId_client() != null) {
                stmt.setInt(3, facture.getId_client());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }

            stmt.executeUpdate();

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
            stmt.setDouble(2, facture.getTotal());
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
                facture.setTotal(rs.getDouble("total"));
                int clientId = rs.getInt("id_client");
                facture.setId_client(rs.wasNull() ? null : clientId);
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
                facture.setTotal(rs.getDouble("total"));
                int clientId = rs.getInt("id_client");
                facture.setId_client(rs.wasNull() ? null : clientId);
                liste.add(facture);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    public List<Integer> ventesMensuelles() {
        List<Integer> ventes = new ArrayList<>();
        for (int i = 0; i < 12; i++) ventes.add(0);

        String sql = "SELECT MONTH(date) AS mois, SUM(total) AS totalVentes FROM Facture GROUP BY MONTH(date)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int mois = rs.getInt("mois");
                double totalVentes = rs.getDouble("totalVentes");
                ventes.set(mois - 1, (int)Math.round(totalVentes));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ventes;
    }

    /**
     * Récupère toutes les lignes d'une facture
     * @param idFacture L'ID de la facture
     * @return Liste des lignes de facture
     */
    public List<LigneFacture> listerLignesFacture(int idFacture) {
        List<LigneFacture> lignes = new ArrayList<>();
        String sql = "SELECT * FROM LigneFacture WHERE id_facture = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idFacture);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                LigneFacture lf = new LigneFacture();
                lf.setId_ligne(rs.getInt("id_ligne"));
                lf.setId_facture(rs.getInt("id_facture"));
                lf.setId_produit(rs.getInt("id_produit"));
                lf.setQuantite(rs.getInt("quantite"));
                lf.setPrixUnitaire(rs.getInt("prixUnitaire"));
                lignes.add(lf);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lignes;
    }
}
