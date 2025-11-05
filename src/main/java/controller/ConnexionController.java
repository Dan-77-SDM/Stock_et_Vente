package controller;

import entities.Administrateur;
import services.AdministrateurService;
import utility.ThymeleafConfig;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;

public class ConnexionController extends HttpServlet {

    private AdministrateurService adminService;

    @Override // Méthode appelée lors de l'initialisation du servlet
    public void init() throws ServletException { // Initialise le service d'administrateur
        adminService = new AdministrateurService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ThymeleafConfig.render(request, response, "formConnexion");// recupère le formulaire de connexion
    }

   @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException { // Traite les données du formulaire de connexion

    String email = request.getParameter("email");
    String motDePasse = request.getParameter("mot_de_passe");

    if (email == null || motDePasse == null || email.isEmpty() || motDePasse.isEmpty()) {
        request.setAttribute("error", "Veuillez remplir tous les champs.");
        ThymeleafConfig.render(request, response, "formConnexion");
        return;
    }

    Administrateur admin = adminService.authentifier(email, motDePasse); // appelle de la methode authentifier 

    if (admin != null) { // != signifie "différent de" cette condition vérifie si l'admin n'est pas null, donc l'authentification a réussi null dans ce sens que "vide" ou "inexistant"
        HttpSession session = request.getSession();
        session.setAttribute("admin", admin);
        response.sendRedirect(request.getContextPath() + "/index");
    } else { // Échec de l'authentification  ceci signifie que l'admin est null. null dans ce sens que "vide" ou "inexistant" donc si le mot de passe ou l'email est incorrect
        request.setAttribute("error", "Email ou mot de passe incorrect"); 
        ThymeleafConfig.render(request, response, "formConnexion");
    }
}

}
