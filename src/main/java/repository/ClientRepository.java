package repository;

import entities.Client;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepository {
// CREATE
public void ajouterClient(Client client) {
    // On suppose que la table s'appelle "client" en minuscules
    String sql = "INSERT INTO Client (nom, email, numero) VALUES (?, ?, ?)";
    System.out.println("Tentative d'ajout du client : " + client);

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        stmt.setString(1, client.getNom());
        stmt.setString(2, client.getEmail());
        // On utilise String pour le numéro pour éviter les problèmes de zéros initiaux
        stmt.setString(3, String.valueOf(client.getNumero())); 

        int affectedRows = stmt.executeUpdate();

        if (affectedRows == 0) {
            System.out.println("Aucune ligne insérée pour le client : " + client);
        } else {
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    client.setId_client(generatedKeys.getInt(1));
                    System.out.println("Client ajouté avec succès, ID=" + client.getId_client());
                } else {
                    System.out.println("Client ajouté mais impossible de récupérer l'ID généré.");
                }
            }
        }

    } catch (SQLException e) {
        System.out.println("Erreur SQL lors de l'ajout du client : " + client);
        e.printStackTrace();
    }
}


    // READ - Trouver un client par ID
    public Client trouverClientParId(int id) {
        String sql = "SELECT * FROM Client WHERE id_client = ?";
        Client client = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                client = new Client(
                    rs.getInt("id_client"),
                    rs.getString("nom"),
                    rs.getString("email"),
                    rs.getInt("numero") // <--- getInt
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    // READ - Récupérer tous les clients
    public List<Client> listerClients() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM Client";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Client client = new Client(
                    rs.getInt("id_client"),
                    rs.getString("nom"),
                    rs.getString("email"),
                    rs.getInt("numero") // <--- getInt
                );
                clients.add(client);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    // UPDATE
    public void modifierClient(Client client) {
        String sql = "UPDATE Client SET nom = ?, email = ?, numero = ? WHERE id_client = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, client.getNom());
            stmt.setString(2, client.getEmail());
            stmt.setInt(3, client.getNumero()); // <--- setInt
            stmt.setInt(4, client.getId_client());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void supprimerClient(int id) {
        String sql = "DELETE FROM Client WHERE id_client = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
