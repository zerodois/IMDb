/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Genre;

/**
 *
 * @author felipe
 */
public class GenreDAO {
    private final Connection conn;
    private final String sql;
    public GenreDAO() throws DAOException {
        this.conn = ConnectionFactory.getConnection();
        this.sql = "select * from genres";
    }
    public ArrayList<Genre> all () throws SQLException {
        ArrayList<Genre> list = new ArrayList<>();
        Statement query = conn.createStatement();
        ResultSet res = query.executeQuery(sql);
        while (res.next()) {
            String name = res.getString("name");
            Genre n = new Genre();
            n.setName(name);
            list.add(n);
        }
        return list;
    }
    public ArrayList<Genre> findByMovie (int id) throws SQLException {
        ArrayList<Genre> arr = new ArrayList<>();
        String find = "select * from genres inner join genresmovies as g on genres.name = g.genre where g.movieid='" + id + "'";
        Statement q = conn.createStatement();
        ResultSet resp = q.executeQuery(find);
        while (resp.next()) {
            Genre g = new Genre();
            g.setName(resp.getString("name"));
            arr.add(g);
        }
        return arr;
    }
}
