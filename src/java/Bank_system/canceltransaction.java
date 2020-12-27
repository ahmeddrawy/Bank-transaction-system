/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bank_system;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ahmed
 */
@WebServlet(name = "canceltransaction", urlPatterns = {"/cancelTransaction"})
public class canceltransaction extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet canceltransaction</title>");            
            out.println("</head>");
            out.println("<body>");
            String BankTransactionID = request.getParameter("TID");
            String confirmed = request.getParameter("confirmed");
            if(confirmed!=null && confirmed.equals("false")){

                if(BankTransactionID != null){

//                    out.print(BankTransactionID);
                    int days =  TransactionORM.getTimeDifference(BankTransactionID);
                    if(days>=1){
                        out.println("can't cancel it's old transaction max 1 day to cancel");
                        out.print("<form action=\"transactions.jsp\" method=\"POST\">"
                                    +"<input type = \"submit\" value = \"show transactinos\" />"
                                    + "</form >");
                    }
                    else {
                            out.println("to confirm dropping transaction "+ BankTransactionID + " please press confirm<br>");
                            out.println("<form action=\"cancelTransaction\">");
                            out.println("<input  type = \"hidden\" name = \"TID\" value = \""+BankTransactionID+"\" >");
                            out.println("<input  type = \"hidden\" name = \"confirmed\" value = \"true\" >");
                            out.println("<td><input type=\"submit\" value=\"confirm\" /></td>");
                            out.println("</form>");
//                        int rows = TransactionORM.drop(BankTransactionID);
//                        if(rows >0){
//                            out.println("");
//                        }
                    }
                }
                else {
                   response.sendRedirect("transactions.jsp");
                }
            }
            else if(confirmed!=null && confirmed.equals("true")){
                int rows = 0 ;
                ResultSet rs = TransactionORM.getTranscation(BankTransactionID);
                if(rs.next()){
                    
                
                    int from = rs.getInt("BTFromAccount");
                    int to = rs.getInt("BTToAccount");
                    ResultSet rsFrom = BankAccountORM.getBankAccount((from+""));
                    ResultSet rsTo = BankAccountORM.getBankAccount(to +"");
                    if(rsFrom.next() && rsTo.next()){
                        double fromBalance = rsFrom.getDouble("BACurrentBalance");
                        double toBalance = rsTo.getDouble("BACurrentBalance");
                        double transferedAmount =rs.getDouble("BTAmount");

                            fromBalance +=transferedAmount ;
                            toBalance -=transferedAmount ;
                            int rowFrom = BankAccountORM.updateCurrentBalance(from+"", fromBalance);
                            int rowTo = BankAccountORM.updateCurrentBalance(to+"", toBalance);
                            rows = TransactionORM.drop(BankTransactionID);


                        if(rows >0){
                            out.println("transaction cancelled successfully ");
                             out.print("<form action=\"transactions.jsp\" method=\"POST\">"
                                        +"<input type = \"submit\" value = \"show transactinos\" />"
                                        + "</form >");
                        }
                        else {
                                                errorMessage(out);

                        }
                    }else {
                                            errorMessage(out);

                    }
                }
                else {
                    errorMessage(out);
                }
            }
            else {
                response.sendRedirect("transactios.jsp");
            }
            out.println("</body>");
            out.println("</html>");
        } catch (SQLException ex) {
            Logger.getLogger(canceltransaction.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    private void errorMessage(PrintWriter out){
        out.println("we faced a problem");
                           out.print("<form action=\"transactions.jsp\" method=\"POST\">"
                                    +"<input type = \"submit\" value = \"show transactinos\" />"
                                    + "</form >");
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
