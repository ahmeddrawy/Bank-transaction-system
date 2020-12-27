<%-- 
    Document   : transactions
    Created on : Dec 26, 2020, 4:25:11 PM
    Author     : ahmed
--%>

<%@page import="Bank_system.BankAccountORM"%>
<%@page import="java.sql.Date"%>
<%@page import="Bank_system.TransactionORM"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Transactions</title>
    </head>
    <body>
        <h1> 
        <%
            Object BAA = session.getAttribute("BankAccountID");
                if(!(BAA == null)){
                    String BankAccountID = BAA.toString();
                    ResultSet rs = BankAccountORM.getBankAccount(BankAccountID);
                   if(rs.next()){
//                       out.print("we get result set");
                        out.print("your balance : " + rs.getDouble("BACurrentBalance"));
                   }else {
                       out.println("error");
                   }
                }
        %>
        
        </h1>
        <h1>Your transactions</h1>
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Amount</th>
                    <th>TO</th>
                    <th>Date</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
<!--            <form action="cancelTransaction">-->
                <%
                    Object BA = session.getAttribute("BankAccountID");
                    if(BA==null){
                        
                        out.println("you don't have a bank account please create one");
                    }
                    else {
                        String BankAccountID = BA.toString();
                        ResultSet rs = TransactionORM.getAllForMe(BankAccountID);
                       if(rs.next()){
                           do{
                               int TID = rs.getInt("BankTransactionID");
                               Date TDate = rs.getDate("BTCreationDate");
                               double TAmount = rs.getDouble("BTAmount");
                               int TOID = rs.getInt("BTToAccount");
                               out.println("<tr>");
                               out.println("<td>"+TID+" </td>");
                               out.println("<td>"+TAmount+" </td>");
                               out.println("<td>"+TOID+" </td>");
                               out.println("<td>"+TDate+" </td>");
                               out.println("<form action=\"cancelTransaction\">");
                               out.println("<input  type = \"hidden\" name = \"TID\" value = \""+TID+"\" >");
                               out.println("<td><input type=\"submit\" value=\"cancel\" /></td>");
                               out.println("</form>");
                               out.println("</tr>");
                           }
                           while(rs.next());
                       }
                       else {
                           out.println("you don't have any transaction yet");
                       }
                    }
                    
                %>
            <!--</form>-->
            </tbody>
        </table>
            <h2>Make a transaction </h2>
            <form action="makeTransaction" method="get">
                <table border="1">
                    <thead>
                        <tr>
                            <th>To</th>
                            <th>Amount</th>
                      
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td><input type="text" name="BTToAccount" value="" /></td>
                            <td><input type="text" name="BTAmount" value="0.0" /></td>
            
                        </tr>
                    </tbody>
                </table>
                <input type="submit" value="submit" />
            </form>

    </body>
</html>
