
package yapspace.models;
import java.io.InputStream;
import yapspace.Post;
import yapspace.User;

import yapspace.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class PostModel {
    
    public static ArrayList<Post> getAllPosts(){
        ArrayList<Post> posts = new ArrayList<>();    
        try {
            // connect to the database
            Connection connection = DatabaseConnection.getConnection();
            
            String query = "select id, text, user_id, timestamp, likes from posts";
            PreparedStatement statement = connection.prepareStatement(query);
            
            ResultSet results = statement.executeQuery();
            
            // get all the attributes for each post
            while (results.next()){
                int id = results.getInt("id");
                String text = results.getString("text");
                Timestamp timestamp = results.getTimestamp("timestamp");
                int likes = results.getInt("likes");
                int userID = results.getInt("user_id");
                
                Post post = new Post(id, text, timestamp, userID, likes);
                posts.add(post);
            }
            results.close();
            statement.close();
            connection.close();
        } catch ( Exception ex){
            System.out.println(ex);
        }
        return posts;
    }
    
    public static String getUsername(Post post){
        String username = null;
        try {
            // connect to the database
            Connection connection = DatabaseConnection.getConnection();
            String query = "select username from user where id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            
            int userID = post.getUserId();
            // set the userid to the query
            statement.setInt(1, userID);

            ResultSet results = statement.executeQuery();
            if(results.next()){
                // get the username from the user id query
                username = results.getString("username");
                System.out.println("Username found: " + username);
            } else {
                System.out.println("No username found for userID: " + userID);
            }
            
            results.close();
            statement.close();
            connection.close();
            
        } catch ( Exception ex){
            ex.printStackTrace();
        }
        return username;
    }
    
    public static void addPost(Post post, InputStream inputStream){
        try {
            // connect to the database
            Connection connection = DatabaseConnection.getConnection();
            String query = "insert into posts ( text, user_id, timestamp, likes, image) " +
                    " values ( ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            
            // set the post attributes to the query
            statement.setString(1, post.getText());
            statement.setInt(2, post.getUserId());
            statement.setTimestamp(3, post.getTimestamp());
            statement.setInt(4, 0);
            
            // if there is an image, set it to the query, otherwise set the blob to be null
            if (inputStream != null) {
                statement.setBlob(5, inputStream);
            } else {
                statement.setNull(5, java.sql.Types.BLOB);
            }

            statement.executeUpdate();
            
            statement.close();
            connection.close();
            
        } catch ( Exception ex){
            ex.printStackTrace();
        }
    }
    
    public static void addLikes(Post post, int likesCount){
        // get the current likes count
        int currentLikesCount = post.getLikes();
        // get the id of the post
        int id = post.getId();
        // add the given likes count to the current likes count
        currentLikesCount += likesCount;
        try {
            // connect to the database
            Connection connection = DatabaseConnection.getConnection();
            String query = "update posts set likes = ? " +
                    " where id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            
            // set the likes count and post id to the query
            statement.setInt(1, currentLikesCount);
            statement.setInt(2, id);
            statement.execute();
            
            statement.close();
            connection.close();
            
        } catch ( Exception ex){
            ex.printStackTrace();
        }
    }
    
    public static Post getPostById(int id) {
        Post post = null;
        try {
            // connect to the database
            Connection connection = DatabaseConnection.getConnection();
            String query = "SELECT id, text, user_id, timestamp, likes FROM posts WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            
            // set the given post id to the query
            statement.setInt(1, id);

            ResultSet results = statement.executeQuery();
            
            // get the attributes of the post
            if (results.next()) {
                String text = results.getString("text");
                Timestamp timestamp = results.getTimestamp("timestamp");
                int userID = results.getInt("user_id");
                int likes = results.getInt("likes");
                post = new Post(id, text, timestamp, userID, likes);
            }

            results.close();
            statement.close();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return post;
    }
    
    public static byte[] getPostImage(int id) {
        // store the image as an array of bytes
        byte[] imageBytes = null;
        try {
            // connect to the database
            Connection connection = DatabaseConnection.getConnection();

            String query = "SELECT image FROM posts WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            
            // set the id to the query
            statement.setInt(1, id);

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
