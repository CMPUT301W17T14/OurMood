package yifanwang.mymood1;

import android.app.Application;

/**
 * Created by junzhuo on 3/19/17.
 */

public class OurMoodApplication extends Application {

    public static String username;
    private Filter_data filter;

    public String getUsername() {
        return username;
    }


    public Filter_data getFilter() {return filter;};
    public void setFilter(Filter_data filter) {this.filter = filter;};
}
