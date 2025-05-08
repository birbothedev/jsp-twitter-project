
package yapspace.models;
import yapspace.DatabaseConnection;
import yapspace.User;
import yapspace.Following;


import java.sql.*;
import java.util.ArrayList;


public class FollowingModel {
    public static ArrayList<Integer> getWhoUserIsFollowing(User user){
        // empty array list to store user ids of who the user is following
        ArrayList<Integer> followingUserIds = new ArrayList<>();
        try {
            // connect to the database
            Connection connection = DatabaseConnection.getConnection();
            String query = "select * from following where followed_by_user_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            
            // get the id of the user
            int followedByID = user.getId();
            // set the user id in the query to the id of the user given
            statement.setInt(1, followedByID);
            
            ResultSet results = statement.executeQuery();
            while (results.next()){
                // get the ids of everyone the user is following and add them to the array list
                followingUserIds.add(results.getInt("following_user_id"));
            } 
            
            results.close();
            statement.close();
            connection.close();  
        } catch ( Exception ex){
            ex.printStackTrace();
        }
        // return the array list
        return followingUserIds;
    }
    
    public static void addFollower(User userWhoIsFollowing, User userWhoIsBeingFollowed) {
        try {
            // if the user is not already following the other user
            if (!isFollowing(userWhoIsFollowing, userWhoIsBeingFollowed)) {
                // connect to the database
                Connection connection = DatabaseConnection.getConnection();
                String query = "insert into following (followed_by_user_id, following_user_id) values (?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                
                // get the ids of the user who is following and the user who is being followed and set their values in the query
                statement.setInt(1, userWhoIsFollowing.getId());
                statement.setInt(2, userWhoIsBeingFollowed.getId());
                // get the number of rows affected
                int rowsAffected = statement.executeUpdate();
                // if there are more than 0 rows affected by the query then we know it worked
                if (rowsAffected > 0) {
                    System.out.println("Follow successful!");
                } else {
                    System.out.println("Follow operation failed.");
                }

                statement.close();
                connection.close();
            } else {
                System.out.println("User is already following this user.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static void unFollow(User userWhoIsUNFollowing, User userWhoIsBeingUNFollowed){
        try {
            // connect to the database
            Connection connection = DatabaseConnection.getConnection();
            String query = "delete from following where followed_by_user_id = ? AND " +
                    " following_user_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            
            // set attributes to the query
            statement.setInt(1, userWhoIsUNFollowing.getId());
            statement.setInt(2, userWhoIsBeingUNFollowed.getId());

            statement.execute();

            statement.close();
            connection.close();

        } catch ( Exception ex){
            ex.printStackTrace();
        }
    }

    public static boolean isFollowing(User userWhoIsFollowing, User userWhoIsBeingFollowed) {
        // initially set boolean to false
        boolean isFollowing = false;
        try {
            // connect to the database
            Connection connection = DatabaseConnection.getConnection();
            String query = "SELECT * FROM following WHERE followed_by_user_id = ? AND following_user_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            // get the ids of the user who is following and the user who is being followed and set their values in the query
            statement.setInt(1, userWhoIsFollowing.getId()); 
            statement.setInt(2, userWhoIsBeingFollowed.getId()); 

            ResultSet results = statement.executeQuery();
            // if there is a result set isFollowing to true
            if (results.next()) {
                isFollowing = true;
            }

            results.close();
            statement.close();
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        // return the boolean
        return isFollowing;
    }

    
    public static ArrayList<Integer> getWhoIsFollowingUser(User user){
        // empty array list to store user ids of who is following the user
        ArrayList<Integer> followers = new ArrayList<>();
        try {
            // connect to the database
            Connection connection = DatabaseConnection.getConnection();
            String query = "select * from following where following_user_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            // get the user id of the user and set it to the query
            statement.setInt(1, user.getId());
            
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                // add followers to the array list
                followers.add(results.getInt("followed_by_user_id"));
            }

            results.close();
            statement.close();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // return the array list
        return followers;
    }
        
    private static String getUsernameById(int userID) {
        String username = null;
        try {
            // connect to the database
            Connection connection = DatabaseConnection.getConnection();
            String query = "select username from user where id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            // set the user id to the query
            statement.setInt(1, userID);

            ResultSet results = statement.executeQuery();
            if (results.next()) {
                // get the username from the user id
                username = results.getString("username");
                System.out.println("Username found: " + username);
            } else {
                System.out.println("No username found for userID: " + userID);
            }

            results.close();
            statement.close();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return username;
    }

    public static String getUsernameOfFollower(Following following) {
        return getUsernameById(following.getFollowedByID());
    }

    public static String getUsernameOfWhoUserIsFollowing(Following following) {
        return getUsernameById(following.getFollowingWhoID());
    }
}
