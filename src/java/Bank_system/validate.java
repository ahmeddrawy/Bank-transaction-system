package Bank_system;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Bank_system.DatabaseConf;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ahmed
 */
@WebServlet(urlPatterns = {"/validate"})
public class validate extends HttpServlet {

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
         PrintWriter out = response.getWriter();
        try  {
            /* TODO output your page here. You may use following sample code. */
            String ID = request.getParameter("ID");
            String password = request.getParameter("password");
            HttpSession session = request.getSession(true);
            Class.forName("com.mysql.jdbc.Driver"); 

           Connection con = new DatabaseConf().getcon();
           Statement stmt = con.createStatement();
           out.println("statement created");
           
           if (ID.equals("") || password.equals("") ){
               
               response.sendRedirect("login.html");
//               RequestDispatcher rd=  request.getRequestDispatcher("index.html");
//               rd.forward(request , response);
           }
           else {
               /// redirect to another page
               /// 
               /**
                    we need to get query on the provided id and password
                    if we get a record we redirect to 
                        customerhome.jsp
                    else 
                        we redirect to login.html 
                * 
                */

                String query = "select * from `customer` where `CustomerID`="+ID + " and `CustomerPassword`='"+password+"';";
                ResultSet rs= stmt.executeQuery(query);
                if(rs.next()){
                   /// we only have one result becuase ID is primary key 
                    session.setAttribute("CustomerID", ID);
                    rs = Bank_system.BankAccountORM.customerHasBankAccount(ID);
                    if(rs.next()){
                        session.setAttribute("BankAccountID",rs.getInt("BankAccountID"));
//                        out.println("BankAccountID "+rs.getInt("BankAccountID"));
                    }
                   
                    response.sendRedirect("customerhome.jsp");
                   
                }
                else {
                    response.sendRedirect("login.html");
                    
                }
               
               
//                RequestDispatcher rd=  request.getRequestDispatcher("show_email_entry.jsp");
//                rd.forward(request , response);
           }
        }
        catch (SQLException ex) {
            out.print("we have  a problem");
            out.print(ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(validate.class.getName()).log(Level.SEVERE, null, ex);
        } 
               finally{
            out.close();
        }
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
