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
import persistence.ActorDAO;
import persistence.DAOException;
import persistence.GenreDAO;

/**
 *
 * @author felipe
 */
@WebServlet(name = "Actor", urlPatterns = {"/actor"})
public class Actor extends HttpServlet {

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
            if (request.getParameter("name") == null)
                throw new DAOException("Parameter <name> expected");
            String filter = request.getParameter("name");

            ActorDAO dao = new ActorDAO();
            ArrayList<model.Actor> acts = dao.find(filter);
            Json json = new Json();
            json.addArray("actors", new ArrayList<>(acts));
            out.print(json.serialize());
        } catch (DAOException | SQLException ex) {
            out.print("{ \"error\": \"" + ex.getMessage() + "\" }");
        }

        out.flush();
    }
}
