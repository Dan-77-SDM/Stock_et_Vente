package services;

import entities.Facture;
import repository.FactureRepository;

import java.util.List;

public class FactureService {

    private FactureRepository repo = new FactureRepository();

    // Ajouter une facture
    public void ajouterFacture(Facture facture) {
        repo.ajouter(facture);
    }

    // Modifier une facture
    public void modifierFacture(Facture facture) {
        repo.modifier(facture);
    }

    // Supprimer une facture par ID
    public void supprimerFacture(int id) {
        repo.supprimer(id);
    }

    // Récupérer une facture par ID
    public Facture getFacture(int id) {
        return repo.trouverParId(id);
    }

    // Récupérer toutes les factures
    public List<Facture> getToutesFactures() {
        return repo.lister();
    }
}
