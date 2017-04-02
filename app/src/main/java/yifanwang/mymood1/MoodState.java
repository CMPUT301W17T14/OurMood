package yifanwang.mymood1;

/**
 * Created by yifanwang on 2017-03-30.
 */

public enum MoodState {
    SAD,
    HAPPY,
    SHAME,
    FEAR,
    ANGER,
    SURPRISED,
    DISGUST,
    CONFUSED;

    @Override
    public String toString() {
        switch(this) {
            case SAD: return "Sad";
            case HAPPY: return "Happy";
            case SHAME: return "Shame";
            case FEAR: return "Fear";
            case ANGER: return "Anger";
            case SURPRISED: return "Surprised";
            case DISGUST: return "Disgust";
            case CONFUSED: return "Confused";
            default: throw new IllegalArgumentException();
        }
    }
}
