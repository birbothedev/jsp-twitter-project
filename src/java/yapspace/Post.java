
package yapspace;

import java.io.Serializable;
import java.sql.*;

public class Post implements Serializable{
    
    private int id;
    private String text;
    private Timestamp timestamp;
    private int userID;
    private int likes;
    private String username;
    
    private String base64postImage;

    public Post(int id, String text, Timestamp timestamp, int userId, int likes) {
        this.id = id;
        this.text = text;
        this.timestamp = timestamp;
        this.userID = userId;
        this.likes = likes;
    }
    
    public Post(int id, String text, Timestamp timestamp, int userId){
        this(id, text, timestamp, userId, 0);
    }

    public String getBase64postImage() {
        return base64postImage;
    }

    public void setBase64postImage(String base64postImage) {
        this.base64postImage = base64postImage;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public int getUserId() {
        return userID;
    }

    public int getLikes() {
        return likes;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getUsername() {
        return username;
    }

}
