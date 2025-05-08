
package yapspace.controllers;
import yapspace.models.UserModel;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import yapspace.HashClass;
import yapspace.User;


public class Login extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        
        // if no action redirect to login page
        if (action == null){
            String url = "/views/login.jsp";
            getServletContext().getRequestDispatcher(url).forward(request, response);
        } else if (action.equalsIgnoreCase("login")){
            // get the username and password from the input fields
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            // if username or password null or empty redirect to error page
            if (username == null || password == null || 
                    username.trim().isEmpty() || password.trim().isEmpty()){
                request.setAttribute("error", "username or password is missing");
                String url = "/views/error.jsp";
                getServletContext().getRequestDispatcher(url).forward(request, response);
                return;
            }
            
            try {
                // hash the password using the hashclass 
                String hashedPassword = HashClass.toHexString(HashClass.getSHA(password));
                // create a user object with a temporary id of 0 (will be added automatically by the database)
                User user = new User(0, username, hashedPassword);
                
                // login the user
                User loggedInUser = UserModel.login(user);
                
                // if user is logged in set the user as the session user
                if (loggedInUser != null){
                    HttpSession session = request.getSession();
                    session.setAttribute("user", loggedInUser);
                    response.sendRedirect("HomeFeed");
                } else {
                    request.setAttribute("error", "Invalid username or password");
                    String url = "/views/error.jsp";
                    getServletContext().getRequestDispatcher(url).forward(request, response);
                }
            } catch (Exception ex){
                request.setAttribute("error", ex);
                String url = "/views/error.jsp";
                getServletContext().getRequestDispatcher(url).forward(request, response);
            }
        } else if (action.equalsIgnoreCase("register")){
            // get the username and password from the input fields
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            // if username or password null or empty redirect to error page
            if (username == null || password == null ||
                    username.trim().isEmpty() || password.trim().isEmpty()){
                request.setAttribute("error", "username or password missing");
                String url = "/views/error.jsp";
                getServletContext().getRequestDispatcher(url).forward(request, response);
                return;
            }
            
            try {
                // this is a lot of repeated code, TODO figure out a way to change this into a method
                String hashedPassword = HashClass.toHexString(HashClass.getSHA(password));
            
                User user = new User(0, username, hashedPassword);
                UserModel.addUser(user);
                
                System.out.println("Registering new user: " + username);
                
                User loggedInUser = UserModel.login(user);
                
                // if user is logged in set the user as the session user
                if (loggedInUser != null){
                    HttpSession session = request.getSession();
                    session.setAttribute("user", loggedInUser);
                    // redirect user to the home feed page after logging in
                    response.sendRedirect("HomeFeed");
                } else {
                    request.setAttribute("error", "Invalid username or password");
                    String url = "/views/error.jsp";
                    getServletContext().getRequestDispatcher(url).forward(request, response);
                }
                
            } catch (NoSuchAlgorithmException ex){
                request.setAttribute("error", ex);
                String url = "/views/error.jsp";
                getServletContext().getRequestDispatcher(url).forward(request, response);
            } 
        }
    }
    
    public static boolean ensureLoggedIn(HttpServletRequest request){
        // return wether or not there is a user object for the session
        HttpSession session = request.getSession();
        User sessionUsername = (User) session.getAttribute("user");
        
        return sessionUsername != null;
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
