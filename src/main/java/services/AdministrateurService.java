package services;

import entities.Administrateur;
import repository.AdministrateurRepository;
import utility.PasswordUtils;

public class AdministrateurService {

    private AdministrateurRepository adminRepository;

    public AdministrateurService() {
        this.adminRepository = new AdministrateurRepository();
    }

    // Inscription
    public boolean enregistrerAdministrateur(Administrateur admin) {
        return adminRepository.save(admin);
    }

    // Connexion (avec vérification du mot de passe haché)
    public Administrateur authentifier(String email, String motDePasse) {
        Administrateur admin = adminRepository.findByEmail(email); // on ne compare plus directement les mots de passe

        if (admin != null && PasswordUtils.checkPassword(motDePasse, admin.getMotDePasse())) {
            return admin;
        }

        return null;
    }
}
