package services;

import entities.Administrateur;
import repository.AdministrateurRepository;
import utility.PasswordUtils;

public class AdministrateurService {

    private AdministrateurRepository adminRepository; // variable pour accéder au repository private ici signifie que cette variable ne peut être accédée que depuis cette classe

    public AdministrateurService() { // constructeur public ici signifie que cette méthode peut être appelée depuis d'autres classes
        this.adminRepository = new AdministrateurRepository(); // initialise le repository
    }

    // Inscription
    public boolean enregistrerAdministrateur(Administrateur admin) { // méthode pour enregistrer un administrateur boolean signifie que cette méthode retourne vrai ou faux donc si l'enregistrement a réussi ou non
        return adminRepository.save(admin);
    }

    // Connexion (avec vérification du mot de passe haché)
    public Administrateur authentifier(String email, String motDePasse) { // méthode pour authentifier un administrateur
        Administrateur admin = adminRepository.findByEmail(email); // on ne compare plus directement les mots de passe

        if (admin != null && PasswordUtils.checkPassword(motDePasse, admin.getMotDePasse())) {// vérifie le mot de passe haché
            return admin;
        }

        return null;
    }
}
