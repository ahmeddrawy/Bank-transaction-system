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
@WebServlet(name = "makeTransaction", urlPatterns = {"/makeTransaction"})
public class makeTransaction extends HttpServlet {

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
            out.println("<title>Servlet makeTransaction</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlets makeTransaction at " + request.getContextPath() + "</h1>");
            /// we need my bank account , the to account check
            //// we need to check if my balance >= amount transfered
            /// then we need to update myBankAccountBalance and TO Balance
            Object BA = request.getSession().getAttribute("BankAccountID");
            if(BA!=null){
                
                String BankAccountID = request.getSession().getAttribute("BankAccountID").toString();
                 ResultSet rsFrom = BankAccountORM.getBankAccount(BankAccountID);
                 String toBankAccountID = request.getParameter("BTToAccount");
                ResultSet rsTo = BankAccountORM.getBankAccount(toBankAccountID);
                if(rsFrom.next() && rsTo.next()){
                    double fromBalance = rsFrom.getDouble("BACurrentBalance");
                    double toBalance = rsTo.getDouble("BACurrentBalance");
                    double transferedAmount =Double.parseDouble( request.getParameter("BTAmount"));
                    if(transferedAmount > 0&&fromBalance >= transferedAmount){
                        fromBalance -=transferedAmount ;
                        toBalance +=transferedAmount ;
                        int rowFrom = BankAccountORM.updateCurrentBalance(BankAccountID, fromBalance);
                        int rowTo = BankAccountORM.updateCurrentBalance(toBankAccountID, toBalance);
                        if(rowFrom > 0 && rowTo >0 ){
                            int rT =  TransactionORM.add(transferedAmount, BankAccountID, toBankAccountID);
                            if(rT > 0 ){
                                out.println("success");
                                 moveback(out);
                            }
                            else {
                                out.println("adding transaction record failed");
                                 moveback(out);
                            }
                        }
                        else {
                            out.println("failed");
                                 moveback(out);
                        }
                    }
                    else {
                        out.print("you don't have enough balance or entered wrong value");
                                 moveback(out);
                    }
                   
                }
                else{
                    out.print("can't process the trasaction");
                                 moveback(out);
                }
            }
            else {
                out.print("you have to login and have a bank account first");
            }
            
            out.println("</body>");
            out.println("</html>");
        } catch (SQLException ex) {
                response.getWriter().print("can't process transaction");
                response.getWriter().print("<form action=\"transactions.jsp\" method=\"POST\">"
                                +"<input type = \"submit\" value = \"show transactinos\" />"
                                + "</form >");
            Logger.getLogger(makeTransaction.class.getName()).log(Level.SEVERE, null, ex);
        }catch(Exception e){
            response.getWriter().print("<form action=\"transactions.jsp\" method=\"POST\">"
                                +"<input type = \"submit\" value = \"show transactinos\" />"
                                + "</form >");
        }
    }
    private void moveback(PrintWriter out){
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
