
package yapspace.models;
import yapspace.User;
import yapspace.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class UserModel {
    
    public static User login(User user){
        try {
            // connect to the database
            Connection connection = DatabaseConnection.getConnection();
            
            String query = "SELECT id, username, password_hash FROM user WHERE username = ? AND password_hash = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            // set the attributes to the query
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());

            ResultSet results = statement.executeQuery();
            // if there are results, return them as a user object
            if (results.next()){
                String password = results.getString("password_hash");
                int id = results.getInt("id");
                String username = results.getString("username");
                System.out.println(" " + id + " " +  username + " " +  password);
                return new User(id, username, password);
            } else {
                System.out.println("No user found for the provided username and password.");
            }
            results.close();
            statement.close();
            connection.close();

        } catch ( Exception ex){
            System.out.println(ex);     
        }
        // if there are no results, return null
        return null;
    }
    
    public static User getUser(String username){
        User user = null;
        try {
            // connect to the database
            Connection connection = DatabaseConnection.getConnection();
            
            String query = "select id, username, password_hash, filename from user " +
                    " where username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            // set the username to the query
            statement.setString(1, username);
            
            ResultSet results = statement.executeQuery();
            
            // if there are results, use the attributes to create a user object
            while (results.next()){
                int id = results.getInt("id");
                String password = results.getString("password_hash");
                String filename = results.getString("filename");
                
                user = new User(id, username, password, filename);
            }
            results.close();
            statement.close();
            connection.close();
        } catch ( Exception ex){
            System.out.println(ex);
        }
        // return the user object
        return user;
    }
    
    public static ArrayList<User> getAllUsers() {
        // create an empty array list to store the users
        ArrayList<User> users = new ArrayList<>();
        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            
            ResultSet results = statement.executeQuery("select * from user");
            
            // while there are results (before the end of the table)
            while (results.next()){
                // get all the user attributes
                int id = results.getInt("id");
                String username = results.getString("username");
                String password = results.getString("password_hash");
                String filename = results.getString("filename");
                
                // create a user object with the attributes
                User user = new User(id, username, password, filename);
                
                // add user to the users arrray list
                users.add(user);
            }
            results.close();
            statement.close();
            connection.close();
            
        } catch ( Exception ex){
            System.out.println(ex);
        }
        // return the array list
        return users;
    }
    
    public static void addUser(User user){
        try {
            // connect to the database
            Connection connection = DatabaseConnection.getConnection();
            String query = "insert into user ( username, password_hash) " +
                    " values ( ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            
            // set attributes to the query
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());

            statement.execute();
            
            statement.close();
            connection.close();
            
        } catch ( Exception ex){
            ex.printStackTrace();
        }
    }
    
    public static void updateUser(User user){
        try {
            // connect to the database
            Connection connection = DatabaseConnection.getConnection();
            String query = "update user set username = ?, password_hash = ? " +
                    " where id = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            // set attributes to the query
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getId());

            statement.execute();

            statement.close();
            connection.close();

        } catch ( Exception ex){
            ex.printStackTrace();
        }
    }
    
    public static void deleteUser(User user){
        try {
            // connect to the database
            Connection connection = DatabaseConnection.getConnection();
            String query = "delete from user where id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            
            // set attributes to the query
            statement.setInt(1, user.getId());

            statement.execute();

            statement.close();
            connection.close();

        } catch ( Exception ex){
            ex.printStackTrace();
        }
    }
    
    public static byte[] getProfileImage(String username) {
        // store the image as an array of bytes
        byte[] imageBytes = null;
        try {
            // connect to the database
            Connection connection = DatabaseConnection.getConnection();

            String query = "SELECT image FROM user WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            // set the username to the query
            statement.setString(1, username);

            ResultSet results = statement.executeQuery();

            if (results.next()) {
                imageBytes = results.getBytes("image"); 
            }

            results.close();
            statement.close();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // return the bytes
        return imageBytes; 
    }
}
