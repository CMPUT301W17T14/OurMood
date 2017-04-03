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
            case SAD: return "sad";
            case HAPPY: return "happy";
            case SHAME: return "shame";
            case FEAR: return "fear";
            case ANGER: return "angry";
            case SURPRISED: return "surprise";
            case DISGUST: return "disgust";
            case CONFUSED: return "confused";
            default: throw new IllegalArgumentException();
        }
    }
}
