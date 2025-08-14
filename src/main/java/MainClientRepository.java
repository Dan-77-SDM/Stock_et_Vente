import entities.Client;
import repository.ClientRepository;

public class MainClientRepository {
    public static void main(String[] args) {
        // Création d'une instance du repository
        ClientRepository clientRepo = new ClientRepository();

        // Création d'un nouveau client
        Client nouveauClient = new Client();
        nouveauClient.setNom("Solomon Grundy");
        nouveauClient.setEmail("solomongrundy@gmail.com");
        nouveauClient.setNumero(33); // numéro en int

        // Tentative d'ajout
        clientRepo.ajouterClient(nouveauClient);

        // Vérification si l'ID a été généré
        if (nouveauClient.getId_client() > 0) {
            System.out.println("✅ L'ajout du client a réussi ! ID généré : " + nouveauClient.getId_client());
        } else {
            System.out.println("❌ L'ajout du client a échoué.");
        }
    }
}
