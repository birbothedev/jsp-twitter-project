package yapspace.controllers;
import yapspace.DatabaseConnection;
import yapspace.User;

import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@MultipartConfig(maxFileSize = 1000000)
public class Upload extends HttpServlet {
    
    // got this code by following along with eric charnesky java database connectivity lecture

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        InputStream inputStream = null;
        String fileName = "";
        
        Part filePart = request.getPart("file");
        if(filePart != null){
            fileName = extractFileName(filePart);
            inputStream = filePart.getInputStream();
        }
        
        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("user") == null) {
                request.setAttribute("error", "You must be logged in to upload a file.");
                getServletContext().getRequestDispatcher("/views/error.jsp").forward(request, response);
                return;
            }
            
            User user = (User) session.getAttribute("user");
            
            String username = user.getUsername();
            
            Connection connection = DatabaseConnection.getConnection();
            String preparedSQL = "update user set image = ?, filename = ? " +
                    " where username = ?";
            PreparedStatement statement = connection.prepareStatement(preparedSQL);
            
            statement.setBlob(1, inputStream);
            statement.setString(2, fileName);
            statement.setString(3, username);
            
            statement.executeUpdate();
            statement.close();
            connection.close();
            
            String url = "/Profile";
            getServletContext().getRequestDispatcher(url).forward(request, response);
        } catch (SQLException ex) {
            request.setAttribute("error", ex.toString());
            String url = "/views/error.jsp";
            getServletContext().getRequestDispatcher(url).forward(request, response);
        } catch (ClassNotFoundException ex){
            request.setAttribute("error", ex.toString());
            String url = "/views/error.jsp";
            getServletContext().getRequestDispatcher(url).forward(request, response);
        }
    }
    
    private String extractFileName(Part part){
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items){
            if (s.trim().startsWith("filename")){
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
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
