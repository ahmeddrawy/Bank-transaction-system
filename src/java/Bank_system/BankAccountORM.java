package Bank_system;


import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ahmed
 */
public class BankAccountORM {
   
    public static ResultSet getBankAccount(String BankAccountID){
        String query = "select * from bankaccounts where BankAccountID="+BankAccountID+";";
        
        return QUERY(query);
    }
    public static ResultSet customerHasBankAccount(String CustomerID){
        Connection con = new DatabaseConf().getcon();
        String query = "select * from bankaccounts where CustomerID="+CustomerID+";";
        
        try {
            Statement stmt = con.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(BankAccountORM.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    public static ResultSet createBankAccount(String CustomerID){
        ResultSet rs= CustomerORM.getCustomer(CustomerID);
        Connection con = new DatabaseConf().getcon();
        try {
            if(rs.next()){
                String query = "INSERT INTO `bankaccounts` (`CustomerID`) VALUES ('"+CustomerID+"');";
                PreparedStatement ps = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);

                    ps.execute();

                ResultSet rss = ps.getGeneratedKeys();
                return rss;

            }
            else{
                return null ;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BankAccountORM.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null ;
    }
    static public int updateCurrentBalance(String BankAccountID, double newBalance){
        Connection con = new DatabaseConf().getcon();
        String query = "UPDATE `bank_system`.`bankaccounts` SET `BACurrentBalance` = '"+newBalance+"' WHERE (`BankAccountID` = '"+BankAccountID+"');";
         try {
            Statement stmt = con.createStatement();
            return stmt.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(BankAccountORM.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0 ;
    }
    static private ResultSet QUERY(String query){
         Connection con = new DatabaseConf().getcon();
//        String query = "select * from bankaccounts where CustomerID="+CustomerID+";";
        
        try {
            Statement stmt = con.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(BankAccountORM.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    
}
