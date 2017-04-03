package yifanwang.mymood1;

import android.app.Application;

/**
 * Created by junzhuo on 3/19/17.
 */

/**
 * this can provide a current username
 */
public class OurMoodApplication extends Application {
    public static String username;
    public static String getUsername() {
        return username;
    }
}
