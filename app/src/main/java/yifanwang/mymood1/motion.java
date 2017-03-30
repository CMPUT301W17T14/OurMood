package yifanwang.mymood1;



import android.graphics.Color;

/**
 * Created by ruoyang on 3/12/17.
 */
public class motion {
    private String motion;
    private int color;
    private String emoji;

    public motion(String motion, String colorString, String emoji) {
        this.motion = motion;
        this.color = Color.parseColor(colorString);
        this.emoji = emoji;
    }

    public String getMotion() {
        return motion;
    }

    public void setMotion(String motion) {
        this.motion = motion;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }
}