/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistence.MovieDAO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import persistence.DAOException;

/**
 *
 * @author felipe
 */
public class Search extends HttpServlet {

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
        String term = request.getParameter("term");
        int page = 0;
        try {
            if (request.getParameter("page") != null)
                page = Integer.parseInt(request.getParameter("page"));
            MovieDAO m = new MovieDAO();
            request.setAttribute("results_per_page", m.getResultsPerPage());
            request.setAttribute("movies", m.search(term, page));
            request.setAttribute("term", term);
            request.setAttribute("total", m.getTotalFound());
            request.setAttribute("page", page);
        } catch (SQLException | DAOException ex) {
            response.sendRedirect("./error.jsp");
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        RequestDispatcher rd = null;
        request.setAttribute("search", term);
        rd = request.getRequestDispatcher("./search.jsp");
        rd.forward(request, response);
    }
}
