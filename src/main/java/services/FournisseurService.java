package services;

import entities.Fournisseur;
import repository.FournisseurRepository;

import java.util.List;

public class FournisseurService {

    private FournisseurRepository fournisseurRepository;

    public FournisseurService() {
        fournisseurRepository = new FournisseurRepository();
    }

    public void ajouterFournisseur(Fournisseur f) {
        fournisseurRepository.ajouterFournisseur(f);
    }

    public void modifierFournisseur(Fournisseur f) {
        fournisseurRepository.modifierFournisseur(f);
    }

    public void supprimerFournisseur(int id) {
        fournisseurRepository.supprimerFournisseur(id);
    }

    public List<Fournisseur> listerFournisseurs() {
        return fournisseurRepository.listerFournisseurs();
    }

    public Fournisseur trouverFournisseurParId(int id) {
        return fournisseurRepository.trouverFournisseurParId(id);
    }
}
