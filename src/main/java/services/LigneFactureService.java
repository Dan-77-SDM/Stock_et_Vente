package services;

import entities.LigneFacture;
import repository.LigneFactureRepository;
import java.util.List;

public class LigneFactureService {
    private LigneFactureRepository repo = new LigneFactureRepository();

    public void ajouterLigne(LigneFacture lf) {
        repo.ajouter(lf);
    }

    public void modifierLigne(LigneFacture lf) {
        repo.modifier(lf);
    }

    public void supprimerLigne(int id) {
        repo.supprimer(id);
    }

    public LigneFacture getLigne(int id) {
        return repo.trouverParId(id);
    }

    public List<LigneFacture> getToutesLignes() {
        return repo.lister();
    }
}
