
package yapspace.controllers;
import yapspace.models.UserModel;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import yapspace.HashClass;
import yapspace.User;


public class YapSpace extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // this page is currently hidden from the user
        // got this code by following along with eric charnesky java database connectivity lecture
        
        String action = request.getParameter("action");
        
        if (!Login.ensureLoggedIn(request)){
            request.setAttribute("message", "you must login");
            response.sendRedirect("Login");
            return;
        }
        
        if (action == null){
            action = "listUsers";
        }
        
        if (action.equalsIgnoreCase("listUsers")){
           
            ArrayList<User> users = UserModel.getAllUsers();
            request.setAttribute("users", users);

            String url = "/views/users.jsp";
            getServletContext().getRequestDispatcher(url).forward(request, response);
        } else if (action.equalsIgnoreCase("updateUser")){
            String id = request.getParameter("id");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            if (id == null || username == null || password == null ||
                    username.trim().isEmpty() || password.trim().isEmpty() || id.trim().isEmpty()){
                request.setAttribute("error", "username or password missing");
                String url = "/views/error.jsp";
                getServletContext().getRequestDispatcher(url).forward(request, response);
                return;
            }
            
            try {
                String hashedPassword = HashClass.toHexString(HashClass.getSHA(password));
            
                User user = new User(Integer.parseInt(id), username, hashedPassword);
                UserModel.updateUser(user);
                
                response.sendRedirect("YapSpace");
                
            } catch (NoSuchAlgorithmException ex){
                handleException(request, response);
            }
        } else if (action.equalsIgnoreCase("deleteUser")){
            String id = request.getParameter("id");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            if (id == null || id.trim().isEmpty()){
                handleException(request, response);
                return;
            }
            
            try {
                User user = new User(Integer.parseInt(id), "", "");
                UserModel.deleteUser(user);
                
                response.sendRedirect("YapSpace");
                
            } catch (Exception ex){
                handleException(request, response);
            }
        }
    }

    public void handleException(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("error", "id is missing");
        String url = "/views/error.jsp";
        getServletContext().getRequestDispatcher(url).forward(request, response);
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
