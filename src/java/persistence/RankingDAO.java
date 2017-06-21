/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Actor;
import model.Director;
import model.Ranking;

/**
 *
 * @author felipe
 */
public class RankingDAO extends DAO{
    
    public RankingDAO () throws DAOException {
        this.conn = ConnectionFactory.getConnection();
    }
    
    public ArrayList<Ranking> get (int X) throws SQLException {
        return get(X, 100, 0);
    }
    
    public ArrayList<Ranking> get (int X, int lim, int off) throws SQLException {
        ArrayList<Ranking> arr = new ArrayList<>();
        String sql = "SELECT * FROM get_ranking(" + X + ", " + lim + ", " + off + ")";
        Statement stmt = conn.createStatement();
        ResultSet res = stmt.executeQuery(sql);
        
        while (res.next()) {
            Actor actor = new Actor();
            Director director = new Director();
            actor.setName( res.getString("actorname") );
            director.setName( res.getString("directorname") );
            int amount = res.getInt("amount");
            
            Ranking r = new Ranking();
            r.setActor(actor);
            r.setDirector(director);
            r.setAmount(amount);
            
            arr.add(r);
        }
        
        res.close();
        stmt.close();
        return arr;
    }
    //SELECT * FROM get_ranking(1000, 100, 0); X, LIM, OFS
    
}
