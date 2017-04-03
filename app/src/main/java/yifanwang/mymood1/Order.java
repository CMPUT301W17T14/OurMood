package yifanwang.mymood1;

import java.util.Comparator;

/**
 * Created by yifanwang on 2017-03-30.
 */

public class Order implements Comparator<Mood> {
    @Override
    public int compare(Mood o1, Mood o2) {
        return o2.getDate().compareTo(o1.getDate());
    }
}
