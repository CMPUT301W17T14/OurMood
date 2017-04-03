package yifanwang.mymood1;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

/**
 * Created by ruoyang on 4/2/17.
 */

public class MoodModelTest extends ActivityInstrumentationTestCase2 {
    public MoodModelTest() {
        super(Mood.class);
    }

    public void testGetter(){
        String username = "test1";
        String motion = "happy";
        Mood mood = new Mood(username,motion);
        assertEquals(mood.getMood(),motion);
        assertEquals(mood.getName(),username);
        String trigger  ="work done";
        mood.setTrigger(trigger);
        assertEquals(mood.getTrigger(),trigger);
        String socail  ="alone";
        mood.setSocial(socail);
        assertEquals(mood.getSocial(),socail);

    }

}
