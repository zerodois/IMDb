/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Data;
import model.Json;
import persistence.ActorDAO;
import persistence.DAOException;
import persistence.DirectorDAO;
import persistence.GenreDAO;
import persistence.LanguageDAO;

/**
 *
 * @author felipe
 */
@WebServlet(name = "Movie", urlPatterns = {"/api/load"})
public class Movie extends HttpServlet {
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

        try {
            if (request.getParameter("id") == null)
                throw new DAOException("Parameter <id> expected");
            int id = Integer.parseInt(request.getParameter("id"));
            Data data = new Data();
            
            GenreDAO gDao = new GenreDAO();
            data.setGenres(gDao.findByMovie(id));
            
            LanguageDAO lDao = new LanguageDAO();
            data.setLangs(lDao.findByMovie(id));
            
            ActorDAO aDao = new ActorDAO();
            data.setActors(aDao.findByMovie(id));

            DirectorDAO dDao = new DirectorDAO();
            data.setDirectors(dDao.findByMovie(id));
            
            Json json = new Json();
            json.addItem("data", data);
            out.print(json.serialize());
        } catch (DAOException | SQLException ex) {
            out.print("{ \"error\": \"" + ex.getMessage() + "\" }");
        }

        out.flush();
    }
}
