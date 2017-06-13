/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import model.Actor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author felipe
 */
public class ActorDAO {
    private final Connection conn;
    public ActorDAO() throws DAOException {
        this.conn = ConnectionFactory.getConnection();
    }
    private String sql (String name) {
        return "select * from actors where name ilike '%" + name + "%'";
    }
    private String stmt (String []ids) {
        String str = "select * from actors where id = " + ids[0];
        for (int i=1; i<ids.length; i++)
            str += " OR id = " + ids[i];
        return str;
    }

    public ArrayList<Actor> find (String []ids) throws SQLException {
        if (ids == null || ids.length == 0)
            return new ArrayList<>();
        return execute(stmt(ids));
    }
    
    public ArrayList<Actor> findByMovie (int id) throws SQLException {
        String str = "select a.* from actors as a inner join actorsmovies as am on a.id=am.actorid where am.movieid='"+id+"'";
        return execute(str);
    }
    
    public ArrayList<Actor> find (String name) throws SQLException {
        return execute(sql(name));
    }
    
    public ArrayList<Actor> execute (String sql) throws SQLException {
        ArrayList<Actor> list = new ArrayList<>();
        Statement query = conn.createStatement();
        ResultSet res = query.executeQuery(sql);
        while(res.next()) {
            Actor n = new Actor();
            n.setName( res.getString("name") );
            n.setId( res.getInt("id") );
            n.setSex( res.getString("sex") );
            list.add(n);
        }
        return list;
    }
}
