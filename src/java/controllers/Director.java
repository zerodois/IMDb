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
import persistence.DirectorDAO;

/**
 *
 * @author felipe
 */
@WebServlet(name = "Director", urlPatterns = {"/api/director"})
public class Director extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

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

            DirectorDAO dao = new DirectorDAO();
            ArrayList<model.Director> dirs = dao.find(filter);
            Json json = new Json();
            json.addArray("directors", new ArrayList<>(dirs));
            out.print(json.serialize());
            
            dao.close();
        } catch (DAOException | SQLException ex) {
            out.print("{ \"error\": \"" + ex.getMessage() + "\" }");
        }

        out.flush();
    }
}
