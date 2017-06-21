/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author felipe
 */
public class DAO {
    protected Connection conn;
    
    public void close () throws SQLException {
        conn.close();
    }
}
