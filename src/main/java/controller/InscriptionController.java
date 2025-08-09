package controller;

import entities.Administrateur;
import services.AdministrateurService;
import utility.PasswordUtils;
import utility.ThymeleafConfig;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;

public class InscriptionController extends HttpServlet {

    private AdministrateurService adminService;

    @Override
    public void init() {
        adminService = new AdministrateurService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ThymeleafConfig.render(request, response, "formInscription");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nom = request.getParameter("nom");
        String email = request.getParameter("email");
        String nomUtilisateur = request.getParameter("nom_utilisateur");
        String motDePasse = request.getParameter("mot_de_passe");
        String confirmPassword = request.getParameter("confirmPassword");

        if (nom == null || email == null || nomUtilisateur == null || motDePasse == null || confirmPassword == null ||
            nom.isEmpty() || email.isEmpty() || nomUtilisateur.isEmpty() || motDePasse.isEmpty() || confirmPassword.isEmpty()) {
            request.setAttribute("error", "Tous les champs sont obligatoires.");
            ThymeleafConfig.render(request, response, "formInscription");
            return;
        }

        if (!motDePasse.equals(confirmPassword)) {
            request.setAttribute("error", "Les mots de passe ne correspondent pas.");
            ThymeleafConfig.render(request, response, "formInscription");
            return;
        }

        // Hashage du mot de passe
        String hashedPassword = PasswordUtils.hashPassword(motDePasse);

        Administrateur admin = new Administrateur(nom, email, nomUtilisateur, hashedPassword);

        boolean inscrit = adminService.enregistrerAdministrateur(admin);

        if (inscrit) {
            // Redirection vers la page d'accueil ou dashboard
            response.sendRedirect("index");
        } else {
            request.setAttribute("error", "Erreur lors de l'inscription, veuillez réessayer.");
            ThymeleafConfig.render(request, response, "formInscription");
        }
    }
}
