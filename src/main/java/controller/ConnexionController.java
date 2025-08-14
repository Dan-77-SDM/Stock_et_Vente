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
        ThymeleafConfig.render(request, response, "formConnexion");
    }

   @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    String email = request.getParameter("email");
    String motDePasse = request.getParameter("mot_de_passe");

    if (email == null || motDePasse == null || email.isEmpty() || motDePasse.isEmpty()) {
        request.setAttribute("erreur", "Veuillez remplir tous les champs.");
        ThymeleafConfig.render(request, response, "formConnexion");
        return;
    }

    Administrateur admin = adminService.authentifier(email, motDePasse);

    if (admin != null) {
        HttpSession session = request.getSession();
        session.setAttribute("admin", admin);
        response.sendRedirect(request.getContextPath() + "/index");
    } else {
        request.setAttribute("erreur", "Email ou mot de passe incorrect");
        ThymeleafConfig.render(request, response, "formConnexion");
    }
}

}
