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
public class TransactionORM {
    static public ResultSet getTranscation(String transactionID){
        String query = "SELECT * FROM bank_system.banktransaction where BankTransactionID="+transactionID+";";
        return TransactionORM.QUERY(query);
    }
    static public ResultSet getAll(){
        String query = "SELECT * FROM bank_system.banktransaction ;";
        return TransactionORM.QUERY(query);
    }
    static public ResultSet getAllForMe(String BankAccountID){
        String query = "SELECT * FROM bank_system.banktransaction where BTFromAccount="+BankAccountID+";";
        return TransactionORM.QUERY(query);
    }
//    static public 
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
    static public int add(double amount,String BTFromAccount , String BTToAccount){
        String query = "INSERT INTO `bank_system`.`banktransaction` (`BTAmount`, `BTFromAccount`, `BTToAccount`)"
                                            + " VALUES ('"+amount+"', '"+BTFromAccount+"', '"+BTToAccount+"');";
         Connection con = new DatabaseConf().getcon();
        try {
            Statement stmt = con.createStatement();
            return stmt.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(BankAccountORM.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0 ;
    }
    static public int getTimeDifference(String BankTransactionID){
        String query = "SELECT DATEDIFF((now()),(select BTCreationDate from banktransaction "
                + "where BankTransactionID="+BankTransactionID+")" +") as nDIFF;";
        ResultSet rs = TransactionORM.QUERY(query);
        try {
            if(rs.next()){
                return rs.getInt("nDIFF");
            }else {
                return -1 ;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TransactionORM.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    static public int drop(String BankTransactionID){
         String query = "DELETE FROM `bank_system`.`banktransaction` WHERE (`BankTransactionID` = '"+BankTransactionID+"');";
         Connection con = new DatabaseConf().getcon();
        try {
            Statement stmt = con.createStatement();
            return stmt.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(BankAccountORM.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0 ;
    }
}
