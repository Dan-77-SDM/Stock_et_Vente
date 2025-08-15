package controller;

import services.ProduitService;
import services.FactureService;
import entities.Produit;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import utility.ThymeleafConfig;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/index")
public class IndexController extends HttpServlet {

    private ProduitService produitService;
    private FactureService factureService;

    @Override
    public void init() throws ServletException {
        produitService = new ProduitService();
        factureService = new FactureService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Total des produits (somme des stocks)
        int totalProduits = produitService.getTotalStock();
        request.setAttribute("totalProduits", totalProduits);

        // Produits en rupture de stock
        int produitsRupture = produitService.getProduitsRuptureStock();
        request.setAttribute("produitsRupture", produitsRupture);

        // Top produits vendus (top 5)
        List<Produit> topProduits = produitService.getTopProduitsVendus(5);
        request.setAttribute("topProduits", topProduits);

        // Évolution des ventes mensuelles
  

List<Integer> ventesMensuelles = factureService.ventesMensuelles(); // index 0 = janvier
Map<String, Integer> ventesMap = new HashMap<>();

String[] mois = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
for (int i = 0; i < 12; i++) {
    ventesMap.put(mois[i], ventesMensuelles.size() > i ? ventesMensuelles.get(i) : 0);
}

request.setAttribute("ventesMensuelles", ventesMap);

        // Render
        ThymeleafConfig.render(request, response, "index");
    }
}
