package yifanwang.mymood1;

/**
 * Created by zheng on 2017-03-12.
 */

/**
 * Created by junzhuo on 3/12/17.
 */

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{

    private String username;
    private ArrayList<User> followlist;
    private ArrayList<Mood> moodlist;
    private ArrayList<String> followeeIDs = new ArrayList<>();
    private ArrayList<String> followerIDs = new ArrayList<>();
    private ArrayList<String> pendingPermissions = new ArrayList<>();

    public ArrayList<User> getFollowlist() {
        return followlist;
    }

    public void setFollowlist(ArrayList<User> followlist) {
        this.followlist = followlist;
    }

    public ArrayList<Mood> getMoodlist() {
        return moodlist;
    }

    public void setMoodlist(ArrayList<Mood> moodlist) {
        this.moodlist = moodlist;
    }

    public User() {

    }

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }


    public ArrayList<String> getFolloweeIDs() {
        return this.followeeIDs;
    }

    /**
     * Sets followee array
     *
     * @param followeeIDs the followee array
     */
    public void setFolloweeIDs(ArrayList<String> followeeIDs) {
        this.followeeIDs.addAll(followeeIDs);
    }

    /**
     * Gets follower array
     *
     * @return the follower array
     */
    public ArrayList<String> getFollowerIDs() {
        return followerIDs;
    }

    /**
     * Sets follower array
     *
     * @param followerIDs the follower array
     */
    public void setFollowerIDs(ArrayList<String> followerIDs) {
        this.followerIDs.addAll(followerIDs);
    }

    /**
     * Gets pending permission array.
     *
     * @return the pending permission array
     */
    public ArrayList<String> getPendingPermission() {
        return pendingPermissions;
    }

    /**
     * Sets pending permissions.
     *
     * @param pendingPermissions the pending permissions
     */
    public void setPendingPermissions(ArrayList<String> pendingPermissions) {
        this.pendingPermissions = pendingPermissions;
    }


    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        /**
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return username.equals(user.username);
*/
        if(o == null) return false;
        if(o == this) return true;
        if(!(o instanceof User)) return false;
        User otherUser = (User) o;
        if(otherUser.getUsername().compareTo(this.getUsername())  == 0) return true;

        return false;
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void addFollow(User user){
        this.followlist.add(user);
    }
    public void deleteFollow(User user){
        this.followlist.remove(user);
    }

    public boolean haveFollow(User user){
        return this.followlist.contains(user);
    }

    public void addMood(Mood mood){
        this.moodlist.add(mood);
    }
    public void deleteMood(Mood mood){
        this.moodlist.remove(mood);
    }
    public boolean haveMood(Mood mood){
        return this.moodlist.contains(mood);
    }
}
