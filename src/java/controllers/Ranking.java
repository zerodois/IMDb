/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Json;
import persistence.DAOException;
import persistence.RankingDAO;

/**
 *
 * @author felipe
 */
@WebServlet(name = "Ranking", urlPatterns = {"/api/ranking"})
public class Ranking extends HttpServlet {
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        int items = 100;
        int X = 100, page = 0;
        try {
            if (request.getParameter("X") == null || request.getParameter("page") == null)
                throw new DAOException("Parameter <X> and <page> expected");
            X = Integer.parseInt(request.getParameter("X"));
            page = Integer.parseInt(request.getParameter("page"));
            RankingDAO dao = new RankingDAO();
            ArrayList<model.Ranking> arr = dao.get(X, items, (page-1)*items);
            Json resp = new Json();
            resp.addArray("rankings", new ArrayList<>(arr));
            out.print(resp.serialize());
        } catch (DAOException | SQLException ex) {
            out.print("{ \"error\": \"" + ex.getMessage() + "\" }");
        }
    }
}
