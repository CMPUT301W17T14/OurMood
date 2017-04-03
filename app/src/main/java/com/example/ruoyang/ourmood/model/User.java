package com.example.ruoyang.ourmood.model;

import java.util.ArrayList;

/**
 * Created by ruoyang on 2/22/17.
 */
public class User {
    private String username;
    private ArrayList<User> followlist;
    private ArrayList<Mood> moodlist;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<User> getFollowlist() {
        return followlist;
    }

    public ArrayList<Mood> getMoodlist() {
        return moodlist;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFollowlist(ArrayList<User> followlist) {
        this.followlist = followlist;
    }

    public void setMoodlist(ArrayList<Mood> moodlist) {
        this.moodlist = moodlist;
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

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }
}
