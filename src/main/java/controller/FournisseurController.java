package controller;

import entities.Fournisseur;
import services.FournisseurService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import utility.ThymeleafConfig;

import java.io.IOException;
@WebServlet("/fournisseurs/*")
public class FournisseurController extends HttpServlet {

    private FournisseurService fournisseurService;

    @Override
    public void init() {
        fournisseurService = new FournisseurService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getPathInfo();

        if (path == null || path.equals("/")) {
            request.setAttribute("fournisseurs", fournisseurService.listerFournisseurs());
            request.setAttribute("fournisseur", new Fournisseur());
            ThymeleafConfig.render(request, response, "fournisseur");
        } else if (path.startsWith("/editer/")) {
            int id = Integer.parseInt(path.substring("/editer/".length()));
            Fournisseur f = fournisseurService.trouverFournisseurParId(id);
            request.setAttribute("fournisseurs", fournisseurService.listerFournisseurs());
            request.setAttribute("fournisseur", f);
            ThymeleafConfig.render(request, response, "fournisseur");
        } else if (path.startsWith("/supprimer/")) {
            int id = Integer.parseInt(path.substring("/supprimer/".length()));
            fournisseurService.supprimerFournisseur(id);
            response.sendRedirect(request.getContextPath() + "/fournisseurs");
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // --- DEBUG ---
        System.out.println("doPost FournisseurController appelé");
        request.getParameterMap().forEach((k,v) -> System.out.println(k + " = " + String.join(",", v)));

        String idStr = request.getParameter("id");
        String nom = request.getParameter("nom");
        String telephone = request.getParameter("telephone");
        String email = request.getParameter("email");

        Fournisseur f = new Fournisseur();
        f.setNom(nom);
        f.setTelephone(telephone);
        f.setEmail(email);

        if (idStr != null && !idStr.isEmpty() && !idStr.equals("0")) {
            f.setId_fournisseur(Integer.parseInt(idStr));
            System.out.println("Modification du fournisseur : " + f);
            fournisseurService.modifierFournisseur(f);
        } else {
            System.out.println("Ajout du fournisseur : " + f);
            fournisseurService.ajouterFournisseur(f);
        }

        response.sendRedirect(request.getContextPath() + "/fournisseurs");
    }
}