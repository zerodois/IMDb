/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.sql.*;
import java.util.*;
import model.Movie;
/**
 *
 * @author felipe
 */
public class MovieDAO {
    private final Connection conn;
    private final int results_per_page = 16;
    private int total;
    public MovieDAO() throws DAOException{
      this.conn = ConnectionFactory.getConnection();
    }
    public int getResultsPerPage () {
        return results_per_page;
    }
    public int getTotalFound () {
        return total;
    }
    public List<Movie> search(String title, int page) throws SQLException {
        String sql = "SELECT count(1) over() as total, * FROM movies "
            + "WHERE title NOT ilike '%(VG)%' AND title NOT ILIKE '%(V)%' AND title NOT ILIKE '%(TV)%' AND title NOT ILIKE '%\"%' AND title ilike '%" + title + "%'"
            + "limit " + results_per_page + " offset " + (page * results_per_page);
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(sql);
        List<Movie> list = new ArrayList<>();
        total = 0;
        while (result.next()) {
            if (total == 0)
                total = result.getInt("total");
            Movie m = new Movie();
            m.setId(result.getInt("id"));
            m.setYear(result.getString("year"));
            m.setTitle(result.getString("title"));
            list.add(m);
        }
        return list;
    }
}
