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
import model.Language;

/**
 *
 * @author felipe
 */
public class LanguageDAO {
    private final Connection conn;
    private final String sql;
    public LanguageDAO() throws DAOException {
        this.conn = ConnectionFactory.getConnection();
        this.sql = "select * from languages where name not like '%-%' and name not like '%(%'";
    }
    public ArrayList<Language> all () throws SQLException {
        ArrayList<Language> list = new ArrayList<>();
        Statement query = conn.createStatement();
        ResultSet res = query.executeQuery(sql);
        while(res.next()) {
            String name = res.getString("name");
            Language n = new Language();
            n.setName(name);
            list.add(n);
        }
        return list;
    }
    
    public ArrayList<Language> findByMovie (int id) throws SQLException {
        ArrayList<Language> arr = new ArrayList<>();
        String find = "select * from languages as l inner join langsmovies as lm on l.name = lm.lang where lm.movieid='" + id + "'";
        Statement q = conn.createStatement();
        ResultSet resp = q.executeQuery(find);
        while (resp.next()) {
            Language g = new Language();
            g.setName(resp.getString("name"));
            arr.add(g);
        }
        return arr;
    }
}
