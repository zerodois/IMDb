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
import model.Director;

/**
 *
 * @author felipe
 */
public class DirectorDAO {
    private final Connection conn;
    public DirectorDAO() throws DAOException {
        this.conn = ConnectionFactory.getConnection();
    }
    private String sql (String name) {
        return "select * from directors where name ilike '%" + name + "%'";
    }
    private String stmt (String []ids) {
        String str = "select * from directors where id = " + ids[0];
        for (int i=1; i<ids.length; i++)
            str += " OR id = "+ids[i];
        return str;
    } 
    public ArrayList<Director> find (String name) throws SQLException {
        return execute(sql(name));
    }
    public ArrayList<Director> findByMovie (int id) throws SQLException {
        String str = "select d.*, dm.addition from directors as d inner join directorsmovies as dm on d.id=dm.directorid where dm.movieid='"+id+"'";
        return execute(str, true);
    }
    public ArrayList<Director> find (String []ids) throws SQLException {
        if (ids == null || ids.length == 0)
            return new ArrayList<>();
        return execute(stmt(ids));
    } 

    public ArrayList<Director> execute (String sql) throws SQLException {
        return execute(sql, false);
    }
    
    public ArrayList<Director> execute (String sql, boolean hasAddition) throws SQLException {
        ArrayList<Director> list = new ArrayList<>();
        Statement query = conn.createStatement();
        ResultSet res = query.executeQuery(sql);
        while(res.next()) {
            Director n = new Director();
            n.setName( res.getString("name") );
            n.setId( res.getInt("id") );
            
            if (hasAddition) {
                n.setAddition( res.getString("addition") );
            }
            
            list.add(n);
        }
        return list;
    }

}
