/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bank_system;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ahmed
 */
public class CustomerORM {
    public  static ResultSet getCustomer(String CustomerID){
        Connection con = new DatabaseConf().getcon();
        String query = "select * from customer where CustomerID="+CustomerID+";";
        
        try {
            Statement stmt = con.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(BankAccountORM.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
}
