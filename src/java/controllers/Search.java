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
import persistence.MovieDAO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import persistence.ActorDAO;
import persistence.DAOException;
import persistence.DirectorDAO;

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
        String title = request.getParameter("title");
        model.Search bean = new model.Search();
        int page = 0;
        final int results_per_page = 16;
        try {
            if (request.getParameter("page") != null)
                page = Integer.parseInt(request.getParameter("page"));
            bean.setTitle(title);
            bean.setGenre(request.getParameter("genre"));
            bean.setLanguage(request.getParameter("language"));
            bean.setYear(request.getParameter("year"));
            bean.setDirectors(request.getParameterValues("directors"));
            bean.setActors(request.getParameterValues("actors"));
            bean.setCategory(request.getParameter("category"));
            bean.setResults_per_page(results_per_page);
            bean.setPage(page);
            
            MovieDAO m = new MovieDAO();
            DirectorDAO directorAPI = new DirectorDAO();
            ArrayList<model.Director> directors = directorAPI.find(bean.getDirectors());
            
            ActorDAO actorAPI = new ActorDAO();
            ArrayList<model.Actor> actors = actorAPI.find(bean.getActors());
    
            boolean empty = bean.isEmpty();
            
            request.setAttribute("empty", empty);
            request.setAttribute("results_per_page", results_per_page);
            request.setAttribute("movies", m.search(bean));
            request.setAttribute("directors", directors);
            request.setAttribute("actors", actors);
            request.setAttribute("term", title);
            request.setAttribute("total", m.getTotalFound());
            request.setAttribute("page", page);
            
            directorAPI.close();
            actorAPI.close();
            m.close();
        } catch (SQLException | DAOException ex) {
            response.sendRedirect("./error.jsp");
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        RequestDispatcher rd = null;
        request.setAttribute("search", bean);
        rd = request.getRequestDispatcher("./search.jsp");
        rd.forward(request, response);
    }
}
