/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Movie;
import model.Search;
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
    public int getTotalFound () {
        return total;
    }
    public String empty (String s) {
        if (s == null)
            return "";
        return s.replace("'", "''");
    }
    public String formalize(String []v) {
        if (v == null)
            return "null";
        String s = "ARRAY[" + v[0];
        for (int i=1; i < v.length; i++)
            s += ", " + v[i];
        s += "]";
        return s;
    }
        
    public List<Movie> search(Search search) throws SQLException {

        List<Movie> list = new ArrayList<>();
        total = 0;
        if (search.isEmpty())
            return list;
        
        int page = search.getPage();
        int res = search.getResults_per_page();
        String title = empty(search.getTitle());
        String year = empty(search.getYear());
        String genre = empty(search.getGenre());
        String language = empty(search.getLanguage());
        String arr[] = search.getActors();
        String actors = formalize(arr);
        String dir[] = search.getDirectors();
        String directors = formalize(dir);
        String category = search.getCategory();
        if (category == null)
            category = "M";
        String sql = "SELECT * FROM search_movies('"+title+"','"+category+"','"+year+"','"+genre+"','"+language+"',"+directors+", "+actors+","+res+", "+(page*res)+")";
        Logger.getLogger(controllers.Search.class.getName()).log(Level.SEVERE, sql);
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(sql);
        
        while (result.next()) {
            if (total == 0)
                total = result.getInt("total");
            Movie m = new Movie();
            m.setId(result.getInt("id"));
            m.setYear(result.getString("year_movie"));
            m.setTitle(result.getString("title_movie"));
            list.add(m);
        }
        return list;
    }
}
