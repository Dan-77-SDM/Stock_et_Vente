package services;

import entities.Client;
import repository.ClientRepository;

import java.util.List;

public class ClientService {

    private ClientRepository clientRepository;

    public ClientService() {
        this.clientRepository = new ClientRepository();
    }

    public void ajouterClient(Client client) {
        clientRepository.ajouterClient(client);
    }

    public List<Client> listerClients() {
        return clientRepository.listerClients();
    }

    public Client trouverClientParId(int id) {
        return clientRepository.trouverClientParId(id);
    }

    public void supprimerClient(int id) {
        clientRepository.supprimerClient(id);
    }

    public void modifierClient(Client client) {
        clientRepository.modifierClient(client);
    }
}
