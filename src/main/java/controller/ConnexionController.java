package controller;

import entities.Administrateur;
import services.AdministrateurService;
import utility.ThymeleafConfig;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;

public class ConnexionController extends HttpServlet {

    private AdministrateurService adminService;

    @Override
    public void init() throws ServletException {
        adminService = new AdministrateurService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Affiche la page de connexion, message d’erreur si présent
        ThymeleafConfig.render(request, response, "formConnexion");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String motDePasse = request.getParameter("mot_de_passe");

        Administrateur admin = adminService.authentifier(email, motDePasse);

        if (admin != null) {
            // Authentification réussie : stocker dans session et rediriger
            HttpSession session = request.getSession();
            session.setAttribute("admin", admin);
            response.sendRedirect(request.getContextPath() + "/index"); // adapter selon ton mapping
        } else {
            // Authentification échouée : mettre message d’erreur puis afficher la page
            request.setAttribute("erreur", "Email ou mot de passe incorrect");
            ThymeleafConfig.render(request, response, "formConnexion");
        }
    }
}
