package services;

import entities.Facture;
import entities.LigneFacture;
import entities.LigneFactureDTO;
import entities.Produit;
import repository.FactureRepository;

import java.util.ArrayList;
import java.util.List;

public class FactureService {

    private FactureRepository repo = new FactureRepository();

    public void ajouterFacture(Facture f) {
        repo.ajouter(f);
    }

    public void modifierFacture(Facture f) {
        repo.modifier(f);
    }

    public void supprimerFacture(int id) {
        repo.supprimer(id);
    }

    public Facture trouverFactureParId(int id) {
        return repo.trouverParId(id);
    }

    public List<Facture> getToutesFactures() {
        return repo.lister();
    }

    public List<Integer> ventesMensuelles() {
        return repo.ventesMensuelles();
    }


public List<LigneFacture> getLignesFacture(int idFacture) {
    // Appelle le repository pour récupérer les lignes de la facture
    return repo.listerLignesFacture(idFacture);
}


public List<LigneFactureDTO> getLignesFactureDTO(int idFacture) {
    List<LigneFacture> lignes = getLignesFacture(idFacture); // récupère depuis la base
    List<LigneFactureDTO> dtoList = new ArrayList<>();

    for (LigneFacture lf : lignes) {
        Produit produit = new ProduitService().trouverProduitParId(lf.getId_produit());
        String designation = produit != null ? produit.getDesignation() : "Inconnu";
        LigneFactureDTO dto = new LigneFactureDTO(lf.getId_produit(), designation, lf.getQuantite(), lf.getPrixUnitaire());
        dtoList.add(dto);
    }

    return dtoList;
}

public void mettreAJourTotal(int idFacture) {
    // Récupère toutes les lignes de la facture
    List<LigneFacture> lignes = getLignesFacture(idFacture);
    
    // Calcule le total
    double total = 0;
    for (LigneFacture lf : lignes) {
        total += lf.getQuantite() * lf.getPrixUnitaire();
    }
    
    // Récupère la facture
    Facture facture = trouverFactureParId(idFacture);
    if (facture != null) {
        facture.setTotal(total);
        modifierFacture(facture); // Met à jour la facture dans la base
    }
}


}
