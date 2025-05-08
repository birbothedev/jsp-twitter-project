
package yapspace.controllers;
import yapspace.DatabaseConnection;

import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

public class GetImage extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        
        // got this code by following along with eric charnesky sessions and images jsp lecture
        
        try {
            Connection connection = DatabaseConnection.getConnection();
            String preparedSQL = "select image, filename from user where username = ?";
            PreparedStatement statement = connection.prepareStatement(preparedSQL);
            
            statement.setString(1, username);
            
            ResultSet result = statement.executeQuery();
            Blob blob = null;
            String filename = "";
            while (result.next()){
                blob = result.getBlob("image");
                filename = result.getString("filename");

            }
            
            byte[] imageBytes = blob.getBytes(1, (int)blob.length());
            
            statement.close();
            connection.close();
            
            String contentType = this.getServletContext().getMimeType(filename);
            response.setHeader("Content-Type", contentType);
            
            OutputStream os = response.getOutputStream();
            os.write(imageBytes);
            os.flush();
            os.close();
            
            
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
