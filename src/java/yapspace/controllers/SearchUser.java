
package yapspace.controllers;
import yapspace.User;
import yapspace.models.UserModel;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SearchUser extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // ensure user is logged in before proceeding
        if (!Login.ensureLoggedIn(request)){
            request.setAttribute("message", "you must login");
            response.sendRedirect("Login");
            return;
        }
        
        // get the username parameter
        String username = request.getParameter("username");
        // if no user is entered redirect to error page
        if (username == null || username.trim().isEmpty()) {
            request.setAttribute("message", "Please enter a username to search.");
            response.sendRedirect("/views/error.jsp");  
            return;
        }
        
        // get the user from the model based on the username entered in the search bar
        User user = UserModel.getUser(username);
        // if no user found in the table redirect to error page
        if (user == null) {
            request.setAttribute("message", "User not found.");
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
            return;
        }
        
        // redirect to the correct profile page
        response.sendRedirect("/YapSpace/Profile?username=" + username);
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
