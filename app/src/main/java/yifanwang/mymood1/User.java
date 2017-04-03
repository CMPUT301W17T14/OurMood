package yifanwang.mymood1;

/**
 * Created by zheng on 2017-03-12.
 */

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{

    private String username;
    private ArrayList<String> followlist;
    private ArrayList<Mood> moodlist=new ArrayList<>();
    private ArrayList<String> followeeIDs = new ArrayList<>();
    private ArrayList<String> followerIDs = new ArrayList<>();
    private ArrayList<String> Pendings = new ArrayList<>();

    public ArrayList<String> getFollowlist() {
        return followlist;
    }


    public ArrayList<Mood> getMoodlist() {
        return moodlist;
    }

    public void addMoodlist(Mood mood) {
        this.moodlist.add(mood);
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
     */
    public void setFolloweeIDs(ArrayList<String> followeeIDs) {
        this.followeeIDs.addAll(followeeIDs);
    }

    /**
     * Gets follower array
     */
    public ArrayList<String> getFollowerIDs() {
        return followerIDs;
    }

    /**
     * Sets follower array
     */
    public void setFollowerIDs(ArrayList<String> followerIDs) {
        this.followerIDs.addAll(followerIDs);
    }

    /**
     * Gets pending array.
     */
    public ArrayList<String> getPending() {
        return Pendings;
    }

    /**
     * Sets pending.
     */
    public void setPendings(ArrayList<String> Pendings) {
        this.Pendings = Pendings;
    }


    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
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
    public void addFollow(String user){
        this.followlist.add(user);
    }
    public void deleteFollow(String user){
        this.followlist.remove(user);
    }

    public boolean haveFollow(String user){
        return this.followlist.contains(user);
    }

    public void addMood(Mood mood){
        this.moodlist.add(mood);
    }
    public void deleteMood(Mood mood){
        ArrayList<Mood> moodarraylist = this.moodlist;
        for(int i=0;i<moodarraylist.size();i++){
            if(moodarraylist.get(i).toString().equals(mood.toString())){
                moodarraylist.remove(i);
            }
        }

    }
    public boolean haveMood(Mood mood){
        return this.moodlist.contains(mood);
    }
}
