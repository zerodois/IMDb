/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Json;
import model.Ranking;
import persistence.DAOException;
import persistence.RankingDAO;

/**
 *
 * @author felipe
 */
@WebServlet(name = "Stats", urlPatterns = {"/stats"})
public class Stats extends HttpServlet {

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
        
        int def = 100;
        if (request.getParameter("X") != null)
            def = Integer.parseInt(request.getParameter("X"));
        ArrayList<Ranking> list;
        Json resp = new Json();

        try {
            RankingDAO rk = new RankingDAO();
            list = rk.get(def);
            resp.addArray("rankings", new ArrayList<Json>(list));
        } catch (DAOException | SQLException ex) {
            response.sendRedirect("./error.jsp");
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        request.setAttribute("X", def);
        request.setAttribute("list", resp.serialize());
        RequestDispatcher rd = request.getRequestDispatcher("./stats.jsp");
        rd.forward(request, response);
    }
}
