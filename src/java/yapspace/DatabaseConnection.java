
package yapspace;

import java.sql.*;

public class DatabaseConnection {
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        
        String dbURL = "jdbc:mysql://localhost:3306/project4";
        
        String username = "yapspace_user";
        String password = "test";
        
        Connection connection = DriverManager.getConnection(
                dbURL, username, password);
        
        return connection;
    }
}
