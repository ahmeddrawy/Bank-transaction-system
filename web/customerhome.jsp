<%-- 
    Document   : customerhome
    Created on : Dec 24, 2020, 10:21:31 PM
    Author     : ahmed
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="Bank_system.BankAccountORM"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Customer home</title>
    </head>
    <body>
        <h1>Hello 
            <%=session.getAttribute("CustomerID")%>
        </h1>
            <% 
                Object BA = session.getAttribute("BankAccountID");
                if(!(BA == null)){
                    String BankAccountID = BA.toString();
                    ResultSet rs = BankAccountORM.getBankAccount(BankAccountID);
                   if(rs.next()){
//                       out.print("we get result set");
                        out.print("your balance : " + rs.getDouble("BACurrentBalance"));
                        out.print("<form action=\"transactions.jsp\" method=\"POST\">"
                                +"<input type = \"submit\" value = \"show transactinos\" />"
                                + "</form >");
                   }else {
                       out.println("error");
                   }
                }
                else {
                    out.print("you don't have a bank account do you want to create an account ?\n");
            %>
                    
       
            <form action="addaccount" method="POST">
                <input type="submit" value="Add Account" />
            </form>
            <%
                }              
            %>
            
            
    </body>
</html>
