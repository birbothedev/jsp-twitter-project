
package yapspace;

import java.io.Serializable;


public class Following implements Serializable{
    private int followedByID;
    private int followingWhoID;

    public Following(int followedByID, int followingWhoID) {
        this.followedByID = followedByID;
        this.followingWhoID = followingWhoID;
    }
    
    public int getFollowedByID() {
        return followedByID;
    }

    public int getFollowingWhoID() {
        return followingWhoID;
    }

}
