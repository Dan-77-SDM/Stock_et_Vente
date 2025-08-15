package controller;

import entities.Facture;
import entities.LigneFactureDTO;
import services.FactureService;
import services.ClientService;
import services.FacturePDFService;
import utility.ThymeleafConfig;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet({"/factures", "/factures/editer", "/factures/supprimer", "/factures/save", "/factures/generer", "/factures/pdf"})
public class FactureController extends HttpServlet {

    private FactureService factureService;
    private ClientService clientService;
    private FacturePDFService pdfService;

    @Override
    public void init() {
        factureService = new FactureService();
        clientService = new ClientService();
        pdfService = new FacturePDFService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        HttpSession session = request.getSession();

        switch (action) {
            case "/factures":
                request.setAttribute("factures", factureService.getToutesFactures());
                request.setAttribute("clients", clientService.listerClients());
                request.setAttribute("facture", new Facture());
                ThymeleafConfig.render(request, response, "factures");
                break;

            case "/factures/editer":
                int idEdit = Integer.parseInt(request.getParameter("id"));
                Facture factureEdit = factureService.trouverFactureParId(idEdit);
                request.setAttribute("factures", factureService.getToutesFactures());
                request.setAttribute("clients", clientService.listerClients());
                request.setAttribute("facture", factureEdit);
                ThymeleafConfig.render(request, response, "factures");
                break;

            case "/factures/supprimer":
                int idSuppr = Integer.parseInt(request.getParameter("id"));
                factureService.supprimerFacture(idSuppr);
                session.setAttribute("message", "Facture supprimée avec succès !");
                response.sendRedirect(request.getContextPath() + "/factures");
                break;

            case "/factures/generer":
                int idGen = Integer.parseInt(request.getParameter("id"));
                Facture factureGen = factureService.trouverFactureParId(idGen);
                List<LigneFactureDTO> lignesGen = factureService.getLignesFactureDTO(idGen);

                request.setAttribute("facture", factureGen);
                request.setAttribute("lignes", lignesGen);
                request.setAttribute("client", clientService.trouverClientParId(factureGen.getId_client()));
                ThymeleafConfig.render(request, response, "facturePDF");
                break;

            case "/factures/pdf":
                int idPdf = Integer.parseInt(request.getParameter("id"));
                Facture facturePdf = factureService.trouverFactureParId(idPdf);
                List<LigneFactureDTO> lignesPdf = factureService.getLignesFactureDTO(idPdf);

                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment; filename=facture_" + idPdf + ".pdf");

                pdfService.genererFactureDansFlux(facturePdf, lignesPdf, response.getOutputStream());
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

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
            double total = Double.parseDouble(totalStr);
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
