package yifanwang.mymood1;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by ruoyang on 4/2/17.
 */
public class UserModelTest extends ActivityInstrumentationTestCase2 {
    public UserModelTest() {
        super(User.class);
    }
    public void test1(){
        User user = new User("A");
        User follower = new User("B");
        Mood mood1 = new Mood("A","happy");

        user.addMood(mood1);
        assertTrue(user.getMoodlist().contains(mood1));

        user.deleteMood(mood1);
        assertFalse(user.getMoodlist().contains(mood1));

        user.addFollow(follower.getUsername());
        assertTrue(user.getFollowlist().contains(follower.getUsername()));

        user.deleteFollow(follower.getUsername());
        assertFalse(user.haveFollow(follower.getUsername()));



    }
}
