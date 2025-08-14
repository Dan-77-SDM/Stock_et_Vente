package controller;

import entities.Facture;
import entities.Client;
import services.FactureService;
import services.ClientService;
import utility.ThymeleafConfig;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet({"/factures", "/factures/editer", "/factures/supprimer", "/factures/save"})
public class FactureController extends HttpServlet {

    private FactureService factureService;
    private ClientService clientService;

    @Override
    public void init() throws ServletException {
        factureService = new FactureService();
        clientService = new ClientService(); // pour remplir le select des clients
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        HttpSession session = request.getSession();

List<Client> clients = clientService.listerClients(); // récupère tous les clients existants
request.setAttribute("clients", clients);

        if (action.equals("/factures")) {
            List<Facture> factures = factureService.getToutesFactures();
            request.setAttribute("factures", factures);
            request.setAttribute("facture", new Facture());
            ThymeleafConfig.render(request, response, "factures");

        } else if (action.equals("/factures/editer")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Facture facture = factureService.getFacture(id);
            request.setAttribute("factures", factureService.getToutesFactures());
            request.setAttribute("facture", facture);
            ThymeleafConfig.render(request, response, "factures");

        } else if (action.equals("/factures/supprimer")) {
            int id = Integer.parseInt(request.getParameter("id"));
            factureService.supprimerFacture(id);
            session.setAttribute("message", "Facture supprimée avec succès !");
            response.sendRedirect(request.getContextPath() + "/factures");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getServletPath();
        HttpSession session = request.getSession();

        if (action.equals("/factures/save")) {
            String idStr = request.getParameter("id_facture");
            String dateStr = request.getParameter("date");
            String totalStr = request.getParameter("total");
            String idClientStr = request.getParameter("id_client");

            if (dateStr == null || dateStr.isEmpty() || totalStr == null || totalStr.isEmpty()) {
                session.setAttribute("error", "Date et total sont obligatoires !");
                response.sendRedirect(request.getContextPath() + "/factures");
                return;
            }

            try {
                java.sql.Date date = java.sql.Date.valueOf(dateStr);
                int total = Integer.parseInt(totalStr);
                Integer idClient = (idClientStr == null || idClientStr.isEmpty()) ? null : Integer.parseInt(idClientStr);

                Facture facture = new Facture();
                facture.setDate(date);
                facture.setTotal(total);
                facture.setId_client(idClient);

                if (idStr == null || idStr.isEmpty() || idStr.equals("0")) {
                    factureService.ajouterFacture(facture);
                    session.setAttribute("message", "Facture ajoutée avec succès !");
                } else {
                    facture.setId_facture(Integer.parseInt(idStr));
                    factureService.modifierFacture(facture);
                    session.setAttribute("message", "Facture modifiée avec succès !");
                }

                response.sendRedirect(request.getContextPath() + "/factures");

            } catch (NumberFormatException e) {
                session.setAttribute("error", "Total ou ID client invalide.");
                response.sendRedirect(request.getContextPath() + "/factures");
            } catch (IllegalArgumentException e) {
                session.setAttribute("error", "Format de date invalide !");
                response.sendRedirect(request.getContextPath() + "/factures");
            }
        }
    }
}
