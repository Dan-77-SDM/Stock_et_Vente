package controller;

import entities.Administrateur;
import services.AdministrateurService;
import utility.*;

import jakarta.servlet.http.*;
import java.io.IOException;

import jakarta.servlet.*;


//@WebServlet(name = "AdministrateurController", urlPatterns = {"/formInscription", "/formConnexion"})
public class AdministrateurController extends HttpServlet {

    private AdministrateurService adminService;

    @Override
    public void init() throws ServletException {
        adminService = new AdministrateurService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        switch (path) {
            case "/formInscription":
                handleInscription(request, response);
                break;
            case "/formConnexion":
                handleConnexion(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void handleInscription(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String nom = request.getParameter("nom");
        String email = request.getParameter("email");
        String nomUtilisateur = request.getParameter("nom_utilisateur");
        String motDePasse = request.getParameter("mot_de_passe");

        String motDePasseHache = PasswordUtils.hashPassword(motDePasse);

        Administrateur admin = new Administrateur(nom, email, nomUtilisateur, motDePasseHache);

        boolean success = adminService.enregistrerAdministrateur(admin);

        if (success) {
            // Redirige vers page de connexion
            response.sendRedirect("formConnexion.html?success=true");
        } else {
            // Renvoie à la même page avec message d’erreur
            response.sendRedirect("formInscription.html?error=true");
        }
    }

    private void handleConnexion(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String email = request.getParameter("email");
        String motDePasse = request.getParameter("mot_de_passe");

        Administrateur admin = adminService.authentifier(email, motDePasse);

        if (admin != null) {
            HttpSession session = request.getSession();
            session.setAttribute("adminConnecte", email);// Stocke l'email de l'administrateur connecté

            // Redirige vers le dashboard
            response.sendRedirect("index.html");
        } else {
            response.sendRedirect("formConnexion.html?erreur=1");// Redirige vers la page de connexion avec une erreur
        }
    }
}
