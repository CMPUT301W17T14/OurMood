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

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return username.equals(user.username);

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
