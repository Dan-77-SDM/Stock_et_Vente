package controller;

import entities.LigneFacture;
import entities.Produit;
import entities.Facture;
import services.LigneFactureService;
import services.ProduitService;
import services.FactureService;
import utility.ThymeleafConfig;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet({"/ligneFacture", "/ligneFacture/editer", "/ligneFacture/supprimer", "/ligneFacture/save"})
public class LigneFactureController extends HttpServlet {

    private LigneFactureService service;
    private ProduitService produitService;
    private FactureService factureService;

    @Override
    public void init() throws ServletException {
        service = new LigneFactureService();
        produitService = new ProduitService();
        factureService = new FactureService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        HttpSession session = request.getSession();

        List<Produit> produits = produitService.listerProduits();
        List<Facture> factures = factureService.getToutesFactures();

        request.setAttribute("produits", produits);
        request.setAttribute("factures", factures);

        if (action.equals("/ligneFacture")) {
            List<LigneFacture> lignes = service.getToutesLignes();
            request.setAttribute("lignesFacture", lignes);
            request.setAttribute("ligneFacture", new LigneFacture());
            ThymeleafConfig.render(request, response, "ligneFacture");

        } else if (action.equals("/ligneFacture/editer")) {
            int id = Integer.parseInt(request.getParameter("id"));
            LigneFacture ligne = service.getLigne(id);
            request.setAttribute("lignesFacture", service.getToutesLignes());
            request.setAttribute("ligneFacture", ligne);
            ThymeleafConfig.render(request, response, "ligneFacture");

        } else if (action.equals("/ligneFacture/supprimer")) {
            int id = Integer.parseInt(request.getParameter("id"));
            service.supprimerLigne(id);
            session.setAttribute("message", "Ligne supprimée avec succès !");
            response.sendRedirect(request.getContextPath() + "/ligneFacture");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        String idStr = request.getParameter("id_ligne");
        String quantiteStr = request.getParameter("quantite");
        String prixStr = request.getParameter("prixUnitaire");
        String idFactureStr = request.getParameter("id_facture");
        String idProduitStr = request.getParameter("id_produit");

        if (quantiteStr == null || quantiteStr.isEmpty() || prixStr == null || prixStr.isEmpty()
                || idFactureStr == null || idFactureStr.isEmpty() || idProduitStr == null || idProduitStr.isEmpty()) {
            session.setAttribute("error", "Tous les champs obligatoires doivent être remplis !");
            response.sendRedirect(request.getContextPath() + "/ligneFacture");
            return;
        }

        try {
            int quantite = Integer.parseInt(quantiteStr);
            int prixUnitaire = Integer.parseInt(prixStr);
            int idFacture = Integer.parseInt(idFactureStr);
            int idProduit = Integer.parseInt(idProduitStr);

            LigneFacture ligne = new LigneFacture();
            ligne.setQuantite(quantite);
            ligne.setPrixUnitaire(prixUnitaire);
            ligne.setId_facture(idFacture);
            ligne.setId_produit(idProduit);

            if (idStr == null || idStr.isEmpty() || idStr.equals("0")) {
                service.ajouterLigne(ligne);
                session.setAttribute("message", "Ligne ajoutée avec succès !");
            } else {
                ligne.setId_ligne(Integer.parseInt(idStr));
                service.modifierLigne(ligne);
                session.setAttribute("message", "Ligne modifiée avec succès !");
            }

            response.sendRedirect(request.getContextPath() + "/ligneFacture");

        } catch (NumberFormatException e) {
            session.setAttribute("error", "Quantité, prix, ID produit ou ID facture invalide.");
            response.sendRedirect(request.getContextPath() + "/ligneFacture");
        }
    }
}
