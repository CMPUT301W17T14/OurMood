package yifanwang.mymood1;

import java.util.Comparator;

/**
 * Created by yifanwang on 2017-04-01.
 */

/**
 * it's our wow function that can rank the mood events in order
 */
public class RankOrder implements Comparator<Mood> {
    @Override
    public int compare(Mood o1, Mood o2) {
        return o1.getLike()>o2.getLike()?-1:1;
    }
}
