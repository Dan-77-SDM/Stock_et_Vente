package controller;

import entities.Produit;
import entities.Fournisseur;
import services.ProduitService;
import services.FournisseurService;
import utility.ThymeleafConfig;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet({"/produits", "/produits/editer", "/produits/supprimer", "/produits/save"})
public class ProduitController extends HttpServlet {

    private ProduitService produitService;
    private FournisseurService fournisseurService;

    @Override
    public void init() throws ServletException {
        produitService = new ProduitService();
        fournisseurService = new FournisseurService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        HttpSession session = request.getSession();

        List<Fournisseur> fournisseurs = fournisseurService.listerFournisseurs();
        request.setAttribute("fournisseurs", fournisseurs);

        if (action.equals("/produits")) {
            List<Produit> produits = produitService.listerProduits();
            request.setAttribute("produits", produits);
            request.setAttribute("produit", new Produit());
            ThymeleafConfig.render(request, response, "produits");

        } else if (action.equals("/produits/editer")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Produit produit = produitService.trouverProduitParId(id);
            request.setAttribute("produits", produitService.listerProduits());
            request.setAttribute("produit", produit);
            ThymeleafConfig.render(request, response, "produits");

        } else if (action.equals("/produits/supprimer")) {
            int id = Integer.parseInt(request.getParameter("id"));
            produitService.supprimerProduit(id);
            session.setAttribute("message", "Produit supprimé avec succès !");
            response.sendRedirect(request.getContextPath() + "/produits");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        String idStr = request.getParameter("id_produit");
        String nom = request.getParameter("designation");
        String prixStr = request.getParameter("prixUnitaire");
        String stockStr = request.getParameter("stock");
        String idFournStr = request.getParameter("id_fournisseur");

        if (nom == null || nom.isEmpty() || prixStr == null || prixStr.isEmpty() || stockStr == null || stockStr.isEmpty()) {
            session.setAttribute("error", "Tous les champs obligatoires doivent être remplis !");
            response.sendRedirect(request.getContextPath() + "/produits");
            return;
        }

        try {
            double prix = Double.parseDouble(prixStr);
            int stock = Integer.parseInt(stockStr);
            Integer idFournisseur = (idFournStr == null || idFournStr.isEmpty()) ? null : Integer.parseInt(idFournStr);

            Produit produit = new Produit();
            produit.setDesignation(nom);
            produit.setPrix_Unitaire(prix);
            produit.setStock(stock);
            produit.setId_fournisseur(idFournisseur);

            if (idStr == null || idStr.isEmpty() || idStr.equals("0")) {
                produitService.ajouterProduit(produit);
                session.setAttribute("message", "Produit ajouté avec succès !");
            } else {
                produit.setId_produit(Integer.parseInt(idStr));
                produitService.modifierProduit(produit);
                session.setAttribute("message", "Produit modifié avec succès !");
            }

            response.sendRedirect(request.getContextPath() + "/produits");

        } catch (NumberFormatException e) {
            session.setAttribute("error", "Prix ou stock invalide.");
            response.sendRedirect(request.getContextPath() + "/produits");
        }
    }
}
