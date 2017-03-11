package com.example.ruoyang.ourmood;

import android.test.ActivityInstrumentationTestCase2;

import com.example.ruoyang.ourmood.model.Mood;
import com.example.ruoyang.ourmood.model.User;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ruoyang on 2/22/17.
 */
public class UserTest extends ActivityInstrumentationTestCase2{
    public UserTest(){
        super(User.class);
    }
    public void testHaveFollow(){
        User user = new User("A");
        User follower = new User("B");
        ArrayList<User> followlist = new ArrayList<User>();
        followlist.add(follower);
        user.setFollowlist(followlist);
        assertEquals(true,user.haveFollow(follower));
    }

    public void testAddFollow(){
        User user = new User("A");
        User follower = new User("B");
        ArrayList<User> followlist = new ArrayList<User>();
        user.setFollowlist(followlist);
        user.addFollow(follower);
        assertTrue(user.haveFollow(follower));
    }
    public void testDeleteFollow(){
        User user = new User("A");
        User follower = new User("B");
        ArrayList<User> followlist = new ArrayList<User>();
        user.setFollowlist(followlist);
        user.addFollow(follower);
        user.deleteFollow(follower);
        assertFalse(user.haveFollow(follower));
    }

    public void testHaveMood(){
        Date currentDateandTime = new Date(System.currentTimeMillis());
        //Time currentTime = new Time();//
        //currentTime.setToNow();//
        User user = new User("A");
        Mood mood = new Mood("happy",currentDateandTime);
        ArrayList<Mood> moodlist = new ArrayList<Mood>();
        moodlist.add(mood);
        user.setMoodlist(moodlist);
        assertEquals(true, user.haveMood(mood));
    }


    public void testAddMood(){
        Date currentDateandTime = new Date(System.currentTimeMillis());
        //Time currentTime = new Time();//
        //currentTime.setToNow();//
        User user = new User("A");
        Mood mood = new Mood("happy",currentDateandTime);
        ArrayList<Mood> moodlist = new ArrayList<Mood>();
        user.setMoodlist(moodlist);

        user.addMood(mood);
        assertTrue(user.haveMood(mood));
    }

    public void testDeleteMood(){
        Date currentDateandTime = new Date(System.currentTimeMillis());
        //Time currentTime = new Time();//
        //currentTime.setToNow();//
        User user = new User("A");
        Mood mood = new Mood("happy",currentDateandTime);
        ArrayList<Mood> moodlist = new ArrayList<Mood>();
        moodlist.add(mood);
        user.setMoodlist(moodlist);

        user.deleteMood(mood);
        assertFalse(user.haveMood(mood));
    }

}
